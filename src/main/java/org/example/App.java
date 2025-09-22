package org.example;

import org.example.printer.MessagePrinter;
import org.example.service.MessageService;
import org.example.service.SMSMessageService;

public class App {
    public static void main(String[] args) {
        MessageService service = new SMSMessageService();
        MessagePrinter printer = new MessagePrinter(service);

        printer.print();
    }
}
