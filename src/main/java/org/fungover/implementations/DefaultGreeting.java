package org.fungover.implementations;

import org.fungover.core.Greeting;
import org.fungover.core.MessageRepo;

public class DefaultGreeting implements Greeting {
    private final MessageRepo repo;

    public DefaultGreeting(MessageRepo repo) {
        this.repo = repo;
    }

    @Override
    public String greet(String name) {
        return repo.fetchMessageFor(name);
    }
}
