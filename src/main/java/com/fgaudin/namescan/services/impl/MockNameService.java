package com.fgaudin.namescan.services.impl;


import com.fgaudin.namescan.domain.Person;
import com.fgaudin.namescan.domain.PersonResult;
import com.fgaudin.namescan.services.NameService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.Collections;
import java.util.List;

@Component
public class MockNameService implements NameService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MockNameService.class);

    @Override
    public List<PersonResult> search(String query) {
        LOGGER.info("search for {}", query);
        if (query.equals("frank")) {
            return Collections.singletonList(mockResult());
        }
        return Collections.emptyList();
    }

    private PersonResult mockResult() {
        PersonResult personResult = new PersonResult();
        personResult.setDataSourceName("INTERPOL");
        Person person = new Person();
        personResult.setPerson(person);
        person.setDateOfBirth(LocalDate.of(1972, Month.DECEMBER, 23));
        person.setFirstName("François");
        person.setLastName("Gaudin");

        return personResult;
    }
}
