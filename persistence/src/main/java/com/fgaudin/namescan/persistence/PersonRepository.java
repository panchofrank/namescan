package com.fgaudin.namescan.persistence;


import com.fgaudin.namescan.domain.Person;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PersonRepository extends MongoRepository<Person, String> {

}
