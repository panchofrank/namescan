package com.fgaudin.namescan.controller;

import com.fgaudin.namescan.domain.PersonResult;


import com.fgaudin.namescan.services.NameService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class NameScanController {

    @Autowired
    private NameService service;

    private static final Logger LOGGER = LoggerFactory.getLogger(NameScanController.class);

    @RequestMapping(path = "/search")
    List<PersonResult> search(@RequestParam String query) {
        LOGGER.debug("search >>>");
        return service.search("frank");

    }
}
