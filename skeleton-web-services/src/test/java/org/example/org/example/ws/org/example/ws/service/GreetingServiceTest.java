package org.example.org.example.ws.org.example.ws.service;

import org.example.org.example.ws.AbstractTest;
import org.example.org.example.ws.model.Greeting;
import org.example.org.example.ws.service.GreetingService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Transactional
public class GreetingServiceTest extends AbstractTest {

    @Autowired
    private GreetingService greetingService;

    @Before
    public void setUp(){
        greetingService.cacheEvict();
    }

    @After
    public void tearDown(){

    }

    @Test
    public void testFindAll(){
        Collection<Greeting> list = greetingService.getAll();

        Assert.assertNotNull("Failure, expected not null", list);
        Assert.assertEquals("Expected to have size ", 2, list.size());
    }

    @Test
    public void testFindOne(){

        Long idToFind = 1L;
        Greeting greeting = greetingService.getOne(idToFind);

        Assert.assertNotNull("Expected not null return", greeting);
        Assert.assertEquals("Expected ID to match the ID of the returned greeting",
                idToFind,
                greeting.getId());
    }
}
