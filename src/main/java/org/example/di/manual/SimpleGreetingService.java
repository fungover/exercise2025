package org.example.di.manual;

import org.example.di.common.GreetingsService;
import org.example.di.common.MessageRepository;

public class SimpleGreetingService implements GreetingsService {
    private final MessageRepository repository;
    public SimpleGreetingService(MessageRepository repository) {
        this.repository = repository;
    }
    @Override
    public String getGreeting(String name) {
        return repository.getMessage() + ", " + name + "!";
    }

}
