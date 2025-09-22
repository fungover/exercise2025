package org.example.service.implementation;

import org.example.persistence.MessageRepository;
import org.example.service.GreetingService;

public class ConsoleGreetingService implements GreetingService {
    private final MessageRepository repository;

    public ConsoleGreetingService(MessageRepository repository) {
        this.repository = repository;
    }

    @Override
    public void greet(String name) {
        String template = repository.getMessage();
        System.out.println(String.format(template, name));
    }
}
