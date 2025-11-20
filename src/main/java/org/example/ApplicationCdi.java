package org.example;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class ApplicationCdi {

    private final MessageService messageService;
    private final Printer printer;


    @Inject
    public ApplicationCdi(MessageService messageService, Printer printer) {
        this.messageService = messageService;
        this.printer = printer;
    }

    public void run() {
        printer.print("[Part 3 / CDI] " + messageService.getMessage());
    }
}
