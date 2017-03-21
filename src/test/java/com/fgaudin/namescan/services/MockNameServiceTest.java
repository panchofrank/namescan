package com.fgaudin.namescan.services;


import com.fgaudin.namescan.domain.PersonResult;
import com.fgaudin.namescan.services.impl.MockNameService;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class MockNameServiceTest {

    @Test
    public void testSearch() {
        MockNameService service = new MockNameService();
        List<PersonResult> results = service.search("frank");
        Assert.assertTrue(results.size() == 1);
        Assert.assertEquals("François", results.get(0).getPerson().getFirstName());
    }

}
