package org.example;

import org.example.service.GreetingService;

public class App {
    private final GreetingService greetingService;

    public App(GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    public void run() {
        greetingService.greet("Johannes");
    }
}
