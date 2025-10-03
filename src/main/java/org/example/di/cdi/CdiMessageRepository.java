package org.example.di.cdi;

import jakarta.enterprise.context.ApplicationScoped;
import org.example.di.common.MessageRepository;

@ApplicationScoped
public class CdiMessageRepository implements MessageRepository {
    @Override
    public String getMessage() {
        return "Hi there";
    }
}
