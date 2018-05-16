package org.example.org.example.ws.org.example.ws.batch;

import org.example.org.example.ws.model.Greeting;
import org.example.org.example.ws.service.GreetingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Profile("batch")
@Component
public class GreetingBatchBean {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private GreetingService greetingService;

    @Scheduled(
            cron = "0,30 * * * * *"
    )
    public void cronJob(){
        logger.info("> Cron job");

        Collection<Greeting> greetings = greetingService.getAll();

        logger.info("There are {} messages in the database", greetings.size());

        logger.info("< Cron job");

    }

}
