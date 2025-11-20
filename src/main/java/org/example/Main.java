package org.example;

import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;

public class Main {

    public static void main(String[] args) {
        // runPart1();
        // runPart2();
        runPart3();
    }

    // PART 1
    private static void runPart1() {
        System.out.println("=== Part 1: Manuell konstruktor-injektion ===");

        MessageService service = new HelloMessageService();
        Printer printer = new ConsolePrinter();
        ApplicationManual app = new ApplicationManual(service, printer);

        app.run();
        System.out.println();
    }

    //  PART 2 (egen container)
    private static void runPart2() {
        System.out.println("=== Part 2: Egen DI-container ===");

        Container container = new Container();
        container.register(MessageService.class, HelloMessageService.class);
        container.register(Printer.class, ConsolePrinter.class);

        ApplicationContainer app = container.getInstance(ApplicationContainer.class);
        app.run();
        System.out.println();
    }

    // PART 3 (Weld CDI)
    private static void runPart3() {
        System.out.println("=== Part 3: Weld CDI ===");

        Weld weld = new Weld();
        try (WeldContainer container = weld.initialize()) {
            ApplicationCdi app = container.select(ApplicationCdi.class).get();
            app.run();
        }

        System.out.println();
    }
}
