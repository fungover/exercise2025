package org.example.persistence.implementation;

import org.example.persistence.MessageRepository;

public class EnglishMessageRepository implements MessageRepository {
    @Override
    public String getMessage() {
        return "Hello, %s!";
    }
}
