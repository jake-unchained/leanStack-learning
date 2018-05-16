package org.example.org.example.ws.org.example.ws.api;

import org.example.org.example.ws.AbstractControllerTest;
import org.example.org.example.ws.service.GreetingService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class GreetingControllerTest extends AbstractControllerTest {

    @Autowired
    private GreetingService greetingService;

    @Before
    public void setup(){
        super.setup();
        greetingService.cacheEvict();
    }

    @Test
    public void testGetGreetings(){
        String uri ="/api.greetings";
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri))
    }
}
