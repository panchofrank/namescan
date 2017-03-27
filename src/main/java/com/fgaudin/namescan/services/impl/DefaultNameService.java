package com.fgaudin.namescan.services.impl;


import com.fgaudin.namescan.domain.Person;
import com.fgaudin.namescan.domain.PersonResult;
import com.fgaudin.namescan.persistence.PersonRepository;
import com.fgaudin.namescan.services.NameService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Month;
import java.util.Collections;
import java.util.List;

@Component
public class DefaultNameService implements NameService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultNameService.class);

    @Autowired
    private PersonRepository repository;

    @Override
    public List<Person> search(String query) {
        LOGGER.info("search for {}", query);
        List<Person> personList = repository.findAll();
        LOGGER.debug("returning {} results", personList.size());
        return personList;
    }


}
