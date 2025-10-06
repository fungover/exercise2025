package org.fungover.implementations;

import org.fungover.core.MessageRepo;

public class InMemoryMessageRepo implements MessageRepo {
    @Override
    public String fetchMessageFor(String name) {
        return "Hello, " + name + "! Welcome to DI.";
    }
}
