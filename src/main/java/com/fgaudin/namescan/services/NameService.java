package com.fgaudin.namescan.services;


import com.fgaudin.namescan.domain.PersonResult;

import java.util.List;

public interface NameService {
    List<PersonResult> search(String query);
}
