package org.example;

public class ApplicationContainer {

    private final MessageService messageService;
    private final Printer printer;

    public ApplicationContainer(MessageService messageService, Printer printer) {
        this.messageService = messageService;
        this.printer = printer;
    }

    public void run() {
        printer.print("[Part 2] " + messageService.getMessage());
    }
}
