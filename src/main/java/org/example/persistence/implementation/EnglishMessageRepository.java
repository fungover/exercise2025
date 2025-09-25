package org.example.persistence.implementation;

import org.example.persistence.MessageRepository;
import jakarta.enterprise.inject.Alternative;

@Alternative
public class EnglishMessageRepository implements MessageRepository {

    @Override
    public String getMessage() {
        return "Hello, %s!";
    }
}
