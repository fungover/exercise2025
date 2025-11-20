package org.example;

public class ApplicationManual {

    private final MessageService messageService;
    private final Printer printer;


    public ApplicationManual(MessageService messageService, Printer printer) {
        this.messageService = messageService;
        this.printer = printer;
    }

    public void run() {
        printer.print(messageService.getMessage());
    }
}
