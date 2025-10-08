package org.example;

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import org.example.weld.WeldInjection;

public class MainWithWeld {
    public static void main(String[] args) {
        try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {
            WeldInjection mw = container.select(WeldInjection.class).get();
            mw.run();
        }
    }
}
