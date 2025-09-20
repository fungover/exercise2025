package org.example;

import org.example.entities.Message;
import org.example.interfaces.MessageRepository;
import org.example.interfaces.MessageService;
import org.example.repositories.DatabaseMessageRepository;
import org.example.repositories.InMemoryMessageRepository;
import org.example.services.EmailService;
import org.example.services.SmsService;

public class App {
    public static void main(String[] args) {
        //In-memory message repository
        MessageRepository messageRepository = new InMemoryMessageRepository();
        MessageService emailService = new EmailService(messageRepository);

        emailService.send(new Message("Hello", "Alfred"));

        System.out.println("--------------------------------");

        //Database message repository
        MessageRepository databaseRepository = new DatabaseMessageRepository();
        MessageService smsService = new SmsService(databaseRepository);

        smsService.send(new Message("Hello", "Alfred"));
    }
}
