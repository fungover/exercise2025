package org.example;

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;

public class Main {
    public static void main(String[] args) {
        try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {
            App app = container.select(App.class).get();
            app.processMessage("Hello from Weld CDI!");
        }
    }
}
