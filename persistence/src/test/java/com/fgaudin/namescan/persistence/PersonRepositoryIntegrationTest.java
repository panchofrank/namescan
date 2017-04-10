package com.fgaudin.namescan.persistence;

import com.fgaudin.namescan.domain.Person;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

/**
 * TODO - move this to an integration test project
 */
@RunWith(SpringRunner.class)
@SpringBootTest(properties={"spring.data.mongodb.host=172.17.0.2"})  // TODO set different values for different profiles
@ContextConfiguration(classes={PersonRepository.class})
@EnableAutoConfiguration
public class PersonRepositoryIntegrationTest {

    @Autowired
    private PersonRepository repository;

    @After
    public void deletAllFromRepo() {
        repository.deleteAll();
    }

    @Test
    public void test_findAll() {
        Person person = new Person();
        person.setFirstName("François");
        person.setLastName("Gaudin");
        person.setDateOfBirth(LocalDate.of(1970, Month.APRIL, 1));
        Person savedPerson = repository.save(person);

        Assert.assertNotNull(savedPerson);
        List<Person> personList = repository.findAll();
        Assert.assertTrue(personList.size() == 1);

    }

    @Test
    public void test_findByNames() {
        Assert.assertEquals(0, repository.findAll().size());
        Person person = new Person();
        person.setFirstName("François");
        person.setLastName("Gaudin");
        person.setDateOfBirth(LocalDate.of(1970, Month.APRIL, 1));
        Person savedPerson = repository.save(person);

        Assert.assertNotNull(savedPerson);
        List<Person> personList = repository.findByFirstNameLikeIgnoreCaseAndLastNameLikeIgnoreCase("François", "Gaudin");
        Assert.assertEquals(1, personList.size());

    }
    @Test
    public void test_findByWildcard() {
        Assert.assertEquals(0, repository.findAll().size());
        Person person = new Person();
        person.setFirstName("François");
        person.setLastName("Gaudin");
        person.setDateOfBirth(LocalDate.of(1970, Month.APRIL, 1));
        Person savedPerson = repository.save(person);

        Assert.assertNotNull(savedPerson);
        List<Person> personList = repository.findByFirstNameLikeIgnoreCaseAndLastNameLikeIgnoreCase("rançois", "Gaudi");
        Assert.assertEquals(1, personList.size());

    }

}
