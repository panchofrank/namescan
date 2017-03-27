package com.fgaudin.namescan.services;


import com.fgaudin.namescan.domain.Person;
import com.fgaudin.namescan.domain.PersonResult;
import com.fgaudin.namescan.persistence.PersonRepository;
import com.fgaudin.namescan.services.impl.DefaultNameService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
public class DefaultNameServiceTest {

    @Mock
    private PersonRepository repository;

    @InjectMocks
    private NameService service = new DefaultNameService();

    @Test
    public void testSearch() {
        List<Person> listPersons = new ArrayList<>();
        Person person = new Person();
        person.setFirstName("François");
        listPersons.add(person);
        Mockito.when(repository.findAll()).thenReturn(listPersons);
        List<Person> results = service.search("frank");
        Assert.assertTrue(results.size() == 1);
        Assert.assertEquals("François", results.get(0).getFirstName());
    }

}
