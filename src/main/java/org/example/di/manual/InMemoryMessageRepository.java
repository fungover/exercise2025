package org.example.di.manual;

import org.example.di.common.MessageRepository;

public class InMemoryMessageRepository implements MessageRepository {
    @Override
    public String getMessage() {
        return "Hello";
    }
}
