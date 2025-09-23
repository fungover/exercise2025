package org.example;

import org.example.persistence.DataRepository;
import org.example.persistence.DatabaseRepository;
import org.example.service.DataService;
import org.example.service.SimpleDataService;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;

public class App {
    public static void main(String[] args) {
        Weld weld = new Weld();
        try (WeldContainer container = weld.initialize()) {
            DataService service = container.select(SimpleDataService.class).get();

        service.process("Hello CDI Part 3 !");
        }
    }
}
