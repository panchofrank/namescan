package com.fgaudin.namescan.controller;

import com.fgaudin.namescan.domain.Person;


import com.fgaudin.namescan.services.NameService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api(value="com.fgaudin.namescan", description="Operations for getting information on persons")
public class NameScanController {

    @Autowired
    private NameService service;

    private static final Logger LOGGER = LoggerFactory.getLogger(NameScanController.class);

    //TODO Paging
    @ApiOperation(value = "View a list of matching persons", response = List.class, produces = "application/json")
    @RequestMapping(path = "/search", method = RequestMethod.GET)
    List<Person> search(@RequestParam String firstName, @RequestParam String lastName) {
        LOGGER.debug("search >>>");
        return service.search(firstName, lastName);

    }
}
