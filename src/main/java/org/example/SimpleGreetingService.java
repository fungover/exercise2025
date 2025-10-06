package org.example;

public class SimpleGreetingService implements GreetingService {
    private final MessageRepository repo;

    public SimpleGreetingService(MessageRepository repo) {
        this.repo = repo;
    }
    
    @Override
    public String greet(String name) {
        return repo.getMessage() + ", " + name + "!";
    }
}
