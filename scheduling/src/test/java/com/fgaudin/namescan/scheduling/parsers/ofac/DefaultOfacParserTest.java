package com.fgaudin.namescan.scheduling.parsers.ofac;

import com.fgaudin.namescan.domain.Person;
import com.fgaudin.namescan.scheduling.parsers.ofac.impl.DefaultOfacParser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
public class DefaultOfacParserTest {

    @Autowired
    private ResourceLoader resourceLoader;

    // TODO
    @Test
    public void test() {

        DefaultOfacParser parser = new DefaultOfacParser();
        Resource resource = resourceLoader.getResource("ofac/sdn.xml");
        List<Person> personList = parser.parse(resource);
        personList.forEach(System.out::println);

    }
}

