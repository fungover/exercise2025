package org.fungover.implementations;

import jakarta.enterprise.context.ApplicationScoped;
import org.fungover.core.MessageRepo;

@ApplicationScoped
public class InMemoryMessageRepo implements MessageRepo {
    @Override
    public String fetchMessageFor(String name) {
        return "Hello, " + name + "! Welcome to DI.";
    }
}
