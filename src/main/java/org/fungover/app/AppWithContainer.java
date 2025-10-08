package org.fungover.app;

import org.fungover.core.Greeting;
import org.fungover.core.MessageRepo;
import org.fungover.di.SimpleContainer;
import org.fungover.implementations.DefaultGreeting;
import org.fungover.implementations.InMemoryMessageRepo;
import org.fungover.model.Greeter;

public class AppWithContainer {
    public static void main(String[] args) {
        var container = new SimpleContainer();

        container.bind(Greeting.class, DefaultGreeting.class);
        container.bind(MessageRepo.class, InMemoryMessageRepo.class);

        var app = container.get(Greeter.class);
        app.run();
    }
}
