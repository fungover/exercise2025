package org.example;

import org.example.printer.MessagePrinter;
import org.example.service.EmailMessageService;
import org.example.service.MessageService;

public class App {
    public static void main(String[] args) {
        MessageService service = new EmailMessageService();
        MessagePrinter printer = new MessagePrinter(service);

        printer.print();
    }
}
