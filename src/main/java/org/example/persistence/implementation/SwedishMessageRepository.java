package org.example.persistence.implementation;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Alternative;
import org.example.persistence.MessageRepository;

@Alternative
@ApplicationScoped
public class SwedishMessageRepository implements MessageRepository {

    @Override
    public String getMessage() {
        return "Hej, %s!";
    }
}
