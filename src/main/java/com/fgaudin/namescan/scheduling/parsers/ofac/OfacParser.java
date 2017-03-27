package com.fgaudin.namescan.scheduling.parsers.ofac;


import com.fgaudin.namescan.domain.Person;
import org.springframework.core.io.Resource;

import java.util.List;

public interface OfacParser {
    public List<Person> parse(Resource resource);
}
