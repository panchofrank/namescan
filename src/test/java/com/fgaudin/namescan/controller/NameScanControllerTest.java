package com.fgaudin.namescan.controller;


import com.fgaudin.namescan.domain.Person;
import com.fgaudin.namescan.domain.PersonResult;
import com.fgaudin.namescan.services.NameService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@WebMvcTest(NameScanController.class)
public class NameScanControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private NameService service;


    @Test
    public void testSearch_with_valid_query() throws Exception {
        given(this.service.search("frank")).willReturn(generateTestResultList());
        this.mvc.perform(get("/search?query=frank").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$[0].firstName", is("François")));
    }

    private List<Person> generateTestResultList() {
        List<Person> list = new ArrayList<>();

        Person person = new Person();

        person.setFirstName("François");
        list.add(person);
        return list;
    }

}
