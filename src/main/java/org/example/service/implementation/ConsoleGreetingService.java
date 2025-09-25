package org.example.service.implementation;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.example.persistence.MessageRepository;
import org.example.service.GreetingService;

@ApplicationScoped
public class ConsoleGreetingService implements GreetingService {
    private final MessageRepository repository;

    @Inject
    public ConsoleGreetingService(MessageRepository repository) {
        this.repository = repository;
    }

    @Override
    public void greet(String name) {
        String template = repository.getMessage();
        System.out.println(String.format(template, name));
    }
}
