package org.example;

import org.example.container.SimpleContainer;
import org.example.printer.MessagePrinter;
import org.example.service.EmailMessageService;
import org.example.service.MessageService;
import org.example.service.SMSMessageService;

public class App {
    public static void main(String[] args) {
        SimpleContainer container = new SimpleContainer();

        container.register(MessageService.class, SMSMessageService.class);

        MessagePrinter printer = container.getInstance(MessagePrinter.class);

        printer.print();
    }
}
