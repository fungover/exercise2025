package org.example;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.example.service.GreetingService;

@ApplicationScoped
public class App {
    private final GreetingService greetingService;

    @Inject
    public App(GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    public void run() {
        greetingService.greet("Johannes!");
    }
}
