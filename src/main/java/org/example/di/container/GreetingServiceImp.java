package org.example.di.container;

import org.example.di.common.GreetingsService;
import org.example.di.common.MessageRepository;

public class GreetingServiceImp implements GreetingsService {
    private final MessageRepository repository;
    public GreetingServiceImp(MessageRepository repository) {
        this.repository = repository;
    }
    @Override
    public String getGreeting(String name) {
        return repository.getMessage() + ", " + name + "!";
    }

}
