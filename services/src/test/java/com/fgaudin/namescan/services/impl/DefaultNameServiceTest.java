package com.fgaudin.namescan.services.impl;

import com.fgaudin.namescan.domain.Person;
import com.fgaudin.namescan.persistence.PersonRepository;
import com.fgaudin.namescan.services.impl.DefaultNameService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
public class DefaultNameServiceTest {

    @Mock
    private PersonRepository repository;

    @InjectMocks
    private DefaultNameService service = new DefaultNameService();

    @Test
    public void testSearch() {
        List<Person> listPersons = new ArrayList<>();
        Person person = new Person();
        person.setFirstName("François");
        listPersons.add(person);
        Mockito.when(repository.findByFirstNameLikeIgnoreCaseAndLastNameLikeIgnoreCase(Matchers.eq("frank"), Matchers.eq("smith"))).thenReturn(listPersons);
        List<Person> results = service.search("frank", "smith");
        Assert.assertEquals(1, results.size());
        Assert.assertEquals("François", results.get(0).getFirstName());
    }

}
