package org.example.org.example.ws.service;

import org.example.org.example.ws.model.Greeting;

import java.util.Collection;

public interface GreetingService {

    Collection<Greeting> getAll();

    Greeting getOne(Long id);

    Greeting create(Greeting greeting);

    Greeting update(Greeting greeting);

    Greeting delete(Greeting greeting);

    void cacheEvict();


}
