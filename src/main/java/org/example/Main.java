package org.example;

public class Main {

    public static void main(String[] args) {
        //  Part 1
         runPart1();

        // Part 2
        runPart2();
    }


    private static void runPart1() {
        System.out.println("=== Part 1: Manuell konstruktor-injektion ===");

        MessageService service = new HelloMessageService();
        Printer printer = new ConsolePrinter();
        ApplicationManual app = new ApplicationManual(service, printer);

        app.run();
        System.out.println();
    }

    private static void runPart2() {
        System.out.println("=== Part 2: DI-container ===");

        Container container = new Container();

        container.register(MessageService.class, HelloMessageService.class);
        container.register(Printer.class, ConsolePrinter.class);


        ApplicationContainer app = container.getInstance(ApplicationContainer.class);

        app.run();
        System.out.println();
    }
}
