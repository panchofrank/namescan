package com.fgaudin.namescan.persistence;


import com.fgaudin.namescan.domain.Person;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PersonRepository extends MongoRepository<Person, String> {

    List<Person> findByFirstNameIgnoreCaseAndLastNameIgnoreCase(String firstNameQuery, String lastNameQuery);

}

