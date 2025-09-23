package org.example;

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import org.example.domain.MessageService;

public class WeldApp {

    public static void main(String[] args) {

        System.out.println("=== Testing Weld CDI ===");

        try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {
            MessageService service = container.select(MessageService.class).get();
            System.out.println("Got implementation: " + service.getClass().getSimpleName());
            service.processMessage();
        }
    }
}
