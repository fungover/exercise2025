package org.example;

import org.example.printer.MessagePrinter;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;

public class App {
    public static void main(String[] args) {
        Weld weld = new Weld();
        try (WeldContainer container = weld.initialize()) {
            MessagePrinter printer = container.select(MessagePrinter.class).get();
            printer.print();
        }
    }
}
