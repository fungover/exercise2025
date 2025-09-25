package org.example.persistence.implementation;

import jakarta.enterprise.context.ApplicationScoped;
import org.example.persistence.MessageRepository;

@ApplicationScoped
public class SwedishMessageRepository implements MessageRepository {

    @Override
    public String getMessage() {
        return "Hej, %s!";
    }
}
