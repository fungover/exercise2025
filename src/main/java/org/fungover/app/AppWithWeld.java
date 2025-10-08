package org.fungover.app;


import jakarta.enterprise.inject.se.SeContainerInitializer;
import jakarta.enterprise.inject.se.SeContainer;
import org.fungover.model.Greeter;

public class AppWithWeld {
    public static void main(String[] args) {
        try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {
            Greeter app = container.select(Greeter.class).get();
            app.run();
        }
    }
}
