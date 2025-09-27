package org.example.part3;

import org.example.qualifiers.Advanced;
import org.example.qualifiers.Simple;
import org.example.service.DataService;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;


public class App {
     static void main(String[] args) {
        try (WeldContainer container = new Weld().initialize()) {
            DataService simple = container.select(DataService.class, new Simple.Literal()).get();
            DataService advanced = container.select(DataService.class, new Advanced.Literal()).get();

            System.out.println("=== Running SimpleDataService ===");
            simple.process("Hello from Simple!");

            System.out.println("=== Running AdvancedDataService ===");
            advanced.process("Hello from Advanced!");
        }
    }
}
