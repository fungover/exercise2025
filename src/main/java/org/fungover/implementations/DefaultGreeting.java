package org.fungover.implementations;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.fungover.core.Greeting;
import org.fungover.core.MessageRepo;

@ApplicationScoped
public class DefaultGreeting implements Greeting {
    private final MessageRepo repo;

    @Inject
    public DefaultGreeting(MessageRepo repo) {
        this.repo = repo;
    }

    @Override
    public String greet(String name) {
        return repo.fetchMessageFor(name);
    }
}
