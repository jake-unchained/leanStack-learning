package org.example.org.example.ws.service;

import org.example.org.example.ws.model.Greeting;

import java.util.concurrent.Future;

public interface EmailService {

    Boolean send(Greeting greeting);

    void sendAsync(Greeting greeting);

    Future<Boolean> sendAsyncWithReturn(Greeting greeting);
}
