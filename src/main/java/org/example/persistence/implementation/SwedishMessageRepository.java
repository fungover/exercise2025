package org.example.persistence.implementation;

import org.example.persistence.MessageRepository;

public class SwedishMessageRepository implements MessageRepository {
    @Override
    public String getMessage() {
        return "Hej, %s!";
    }
}
