package org.example.org.example.ws.web.api;

import org.example.org.example.ws.model.Greeting;
import org.example.org.example.ws.service.GreetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.Media;
import java.math.BigInteger;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
public class GreetingController {

    @Autowired
    private GreetingService greetingService;


    @RequestMapping(
            value="/api/greetings",
            method=RequestMethod.GET,
            produces=MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Collection<Greeting>> getAll(){

        Collection<Greeting> greetings = greetingService.getAll();

        return new ResponseEntity(greetings, HttpStatus.OK);
    }

    @RequestMapping(
            value="/api/greetings/{id}",
            method=RequestMethod.GET,
            produces=MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Greeting> getOne(@PathVariable("id") Long id){

        Greeting greeting = greetingService.getOne(id);
        if( greeting == null ){
            Greeting fuckup = new Greeting();
            fuckup.setText("Youu done fuckeed up son");
            fuckup.setId(420l);

            return new ResponseEntity<>(greeting, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(greeting, HttpStatus.OK);
    }


    @RequestMapping(
            value="/api/greetings",
            method=RequestMethod.POST,
            produces=MediaType.APPLICATION_JSON_VALUE,
            consumes=MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Greeting> create(@RequestBody Greeting greeting){
        if( greeting != null ){
            return new ResponseEntity<>(greetingService.create(greeting), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(
            value="/api/greetings",
            method=RequestMethod.PUT,
            consumes=MediaType.APPLICATION_JSON_VALUE,
            produces=MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Greeting> update(@RequestBody Greeting greeting){
        Greeting updatedGreeting = greetingService.update(greeting);
        if( updatedGreeting == null ){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(updatedGreeting, HttpStatus.OK);
    }

    @RequestMapping(
            value="/api/greetings",
            method=RequestMethod.DELETE,
            consumes=MediaType.APPLICATION_JSON_VALUE,
            produces=MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Greeting> delete(@RequestBody Greeting greeting){
        Greeting deletedGreeting = greetingService.delete(greeting);
        if( deletedGreeting != null ){
            return new ResponseEntity<Greeting>(deletedGreeting, HttpStatus.OK);
        }
        return new ResponseEntity<Greeting>(HttpStatus.INTERNAL_SERVER_ERROR);

    }
}
