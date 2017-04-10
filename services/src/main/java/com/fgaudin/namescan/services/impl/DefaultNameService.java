package com.fgaudin.namescan.services.impl;


import com.fgaudin.namescan.domain.Person;
import com.fgaudin.namescan.persistence.PersonRepository;
import com.fgaudin.namescan.services.NameService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DefaultNameService implements NameService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultNameService.class);

    @Autowired
    private PersonRepository repository;

    public List<Person> search(String firstName, String lastName) {
        LOGGER.info("search for {} {}", firstName, lastName);
        List<Person> personList = repository.findByFirstNameLikeIgnoreCaseAndLastNameLikeIgnoreCase(firstName, lastName);
        LOGGER.debug("returning {} results", personList.size());
        return personList;
    }


}
