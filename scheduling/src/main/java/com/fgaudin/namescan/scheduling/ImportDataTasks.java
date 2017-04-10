package com.fgaudin.namescan.scheduling;

import com.fgaudin.namescan.domain.Person;
import com.fgaudin.namescan.persistence.PersonRepository;
import com.fgaudin.namescan.scheduling.parsers.ofac.OfacParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ImportDataTasks {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImportDataTasks.class);

    // TODO too manay dependencies - possible violation of single responsibility principle

    @Autowired
    private PersonRepository repository;

    @Autowired
    private OfacParser ofacParser;

    @Autowired
    private Downloader ofacDownloader;

    @Autowired
    private Resource ofacResource;

    // TODO put these values in properties file
    @Scheduled(initialDelay = 0, fixedRate=1000 * 60 * 60)
    public void importOfac() {
        LOGGER.info("Importing OFAC data...");

        ofacDownloader.downloadFile();

        repository.deleteAll();
        List<Person> personList = ofacParser.parse(ofacResource);
        repository.save(personList);
    }

}
