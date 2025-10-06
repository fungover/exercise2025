package org.example;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;


@ApplicationScoped
public class SimpleGreetingService implements GreetingService {
    private final MessageRepository repo;

    @Inject
    public SimpleGreetingService(MessageRepository repo) {
        this.repo = repo;
    }
    
    @Override public String greet(String name) {
        return repo.getMessage() + ", " + name + "!";
    }
}
