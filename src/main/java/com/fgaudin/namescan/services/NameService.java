package com.fgaudin.namescan.services;


import com.fgaudin.namescan.domain.Person;
import com.fgaudin.namescan.domain.PersonResult;

import java.util.List;

public interface NameService {
    List<Person> search(String query);
}
