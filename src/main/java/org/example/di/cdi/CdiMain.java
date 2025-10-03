package org.example.di.cdi;

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import org.example.di.common.GreetingsService;

public class CdiMain {
    public static void main(String[] args) {
        try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {
            GreetingsService service = container.select(GreetingsService.class).get();
            System.out.println(service.getGreeting("User"));
        }
    }
}
