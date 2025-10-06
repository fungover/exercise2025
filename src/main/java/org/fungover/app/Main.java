package org.fungover.app;

import org.fungover.implementations.DefaultGreeting;
import org.fungover.implementations.InMemoryMessageRepo;
import org.fungover.model.Greeter;

public class Main {
    public static void main(String[] args) {
        var repo = new InMemoryMessageRepo();
        var service = new DefaultGreeting(repo);
        var app = new Greeter(service);
        app.run();
    }
}
