package org.example.org.example.ws.web.api;

import org.example.org.example.ws.model.Greeting;
import org.example.org.example.ws.service.EmailService;
import org.example.org.example.ws.service.GreetingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.util.concurrent.Future;

@RestController
public class GreetingController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private GreetingService greetingService;

    @Autowired
    private EmailService emailService;


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

    @RequestMapping(
            value = "/api/greetings/{id}/send",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Greeting> sendGreeting(@PathVariable("id") Long id,
                                                 @RequestParam(
                                                         value = "wait",
                                                         defaultValue = "false")
                                                 boolean waitForAsyncResult) {

        logger.info("> sendGreeting id:{}", id);

        Greeting greeting = null;

        try {
            greeting = greetingService.getOne(id);
            if (greeting == null) {
                logger.info("< sendGreeting id:{}", id);
                return new ResponseEntity<Greeting>(HttpStatus.NOT_FOUND);
            }

            if (waitForAsyncResult) {
                Future<Boolean> asyncResponse = emailService
                        .sendAsyncWithReturn(greeting);
                boolean emailSent = asyncResponse.get();
                logger.info("- greeting email sent? {}", emailSent);
            } else {
                emailService.sendAsync(greeting);
            }
        } catch (Exception e) {
            logger.error("A problem occurred sending the Greeting.", e);
            return new ResponseEntity<Greeting>(
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

        logger.info("< sendGreeting id:{}", id);
        return new ResponseEntity<Greeting>(greeting, HttpStatus.OK);
    }
}
