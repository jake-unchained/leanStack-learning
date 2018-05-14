package org.example.org.example.ws.service;

import org.example.org.example.ws.model.Greeting;
import org.example.org.example.ws.org.example.ws.repository.GreetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
@Transactional(
        propagation = Propagation.SUPPORTS,
        readOnly = true
)
public class GreetingServiceBean implements GreetingService {


    @Autowired
    private GreetingRepository greetingRepository;

    @Override
    public Collection<Greeting> getAll() {
        //Collection<Greeting> greetings = greetingMap.values();
        Collection<Greeting> greetings = greetingRepository.findAll();
        return greetings;
    }

    @Override
    @Cacheable(
            value="greetings",
            key="#id"
    )
    public Greeting getOne(Long id) {
        Greeting greeting = greetingRepository.getOne(id);
        return greeting;
    }

    @Override
    @CachePut(
            value="greetings",
            key="#result.id"
    )
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Greeting create(Greeting greeting) {
       //Greeting newGreeting = save(greeting);
        //Cannot create a greeting with a specific ID
        if( greeting.getId() != null ){
            return null;
        }
        Greeting newGreeting = greetingRepository.save(greeting);
        if( newGreeting.getId() == 4L ) {
            throw new RuntimeException("no0B jst G0t Pwn4DDD");
        }

        return newGreeting;
    }

    @Override
    @CachePut(
            value="greetings",
            key="#greeting.id"
    )
    //NOTE: The greeting.id is referring to the method parameter in the update method.
    //      When the method returns, it will use the id of the returned greeting as the key
    //      It will only be stored if the key is already present in cache
    public Greeting update(Greeting greeting) {
        //Greeting updatedGreeting = updateOrSave(greeting);
        //Cannot create a greeting with a specific ID

        Greeting persistedGreeting = greetingRepository.getOne( greeting.getId().longValue() );
        if( persistedGreeting == null ){
            return null;
        }
        Greeting updatedGreeting = greetingRepository.save(greeting);
        return updatedGreeting;

    }

    @Override
    @CacheEvict(
            value="greetings",
            key="#id"
    )
    public Greeting delete(Greeting greeting) {
        greetingRepository.delete(greeting);
        return greeting;
    }

    @Override
    @CacheEvict(
            value="greetings",
            allEntries = true
    )
    public void cacheEvict() {

    }
}
