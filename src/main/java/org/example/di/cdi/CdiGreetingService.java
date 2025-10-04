package org.example.di.cdi;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import org.example.di.common.GreetingsService;
import org.example.di.common.MessageRepository;

@Dependent
public class CdiGreetingService  implements GreetingsService {
    private final MessageRepository repository;
    @Inject
    public CdiGreetingService(MessageRepository repository) {
        if (repository == null) {
            throw new IllegalArgumentException("Repository must not be null");
            }
        this.repository = repository;
    }
    @Override
    public String getGreeting(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name must not be null or blank");
        }
        return repository.getMessage() + ", " + name + "!";
    }
}
