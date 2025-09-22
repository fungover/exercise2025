package org.example.printer;

import jakarta.inject.Inject;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Named;
import org.example.qualifier.SMS;
import org.example.service.MessageService;

@Dependent
public class MessagePrinter {
    private final MessageService service;

    @Inject
    public MessagePrinter(@SMS MessageService service) {
        this.service = service;
    }
    public void print() {
        System.out.println(service.getMessage());
    }
}
