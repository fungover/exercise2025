package org.example.part3;

import org.example.service.DataService;
import org.example.service.SimpleDataService;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;

import javax.xml.crypto.Data;

public class App {
    public static void main(String[] args) {
        Weld weld = new Weld();
        try (WeldContainer container = weld.initialize()) {
            DataService service = container.select(DataService.class).get();

        service.process("Hello CDI Part 3 with alternatives!");
        }
    }
}
