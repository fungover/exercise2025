package org.example;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class InMemoryMessageRepository implements MessageRepository {

    @Override public String getMessage() {
        return "Hello";
    }
}
