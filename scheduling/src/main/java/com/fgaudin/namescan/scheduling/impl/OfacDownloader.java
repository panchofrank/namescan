package com.fgaudin.namescan.scheduling.impl;


import com.fgaudin.namescan.scheduling.Downloader;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.net.URL;

@Component
public class OfacDownloader implements Downloader {

    private final static Logger LOGGER = LoggerFactory.getLogger(OfacDownloader.class);

    @Value("${namescan.ofac.file}")
    private String ofacFilePath;

    @Value("${namescan.ofac.url}")
    private String ofacUrl;

    @Override
    public void downloadFile() {
        File file = new File(this.ofacFilePath);
        try {
            FileUtils.copyURLToFile(new URL(this.ofacUrl), file);
        } catch (IOException e) {
            LOGGER.error("Unable to download file: {}", e);
        }
    }
}
