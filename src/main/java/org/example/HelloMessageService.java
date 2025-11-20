package org.example;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class HelloMessageService implements MessageService {

    @Override
    public String getMessage() {

        return "Hello from CDI (Part 3)!";
    }
}
