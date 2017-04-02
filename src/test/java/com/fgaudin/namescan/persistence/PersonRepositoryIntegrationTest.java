package com.fgaudin.namescan.persistence;

import com.fgaudin.namescan.domain.Person;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

/**
 * TODO - move this to an integration test project
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PersonRepositoryIntegrationTest {

    @Autowired
    private PersonRepository repository;

    // TODO
    @Test
    public void test() {
        Person person = new Person();
        person.setFirstName("François");
        person.setLastName("Gaudin");
        person.setDateOfBirth(LocalDate.of(1970, Month.APRIL, 1));
        Person savedPerson = repository.save(person);
        Assert.assertNotNull(savedPerson);
        List<Person> personList = repository.findAll();
        Assert.assertTrue(personList.size() >= 1);
        repository.delete(savedPerson);
    }
}
