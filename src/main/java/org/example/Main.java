package org.example;

public class Main {
    public static void main(String[] args) {
        MessageService service = new HelloMessageService();
        Printer printer = new ConsolePrinter();

        ApplicationManual app = new ApplicationManual(service, printer);
        app.run();
    }
}
