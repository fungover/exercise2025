package org.example.printer;

import org.example.service.MessageService;

public class MessagePrinter {
    private final MessageService service;

    public MessagePrinter(MessageService service) {
        this.service = service;
    }
    public void print() {
        System.out.println(service.getMessage());
    }
}
