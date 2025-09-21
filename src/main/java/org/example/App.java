package org.example;

import jakarta.enterprise.inject.se.SeContainer;
import org.example.di.SimpleDIContainer;
import org.example.entities.Message;
import org.example.interfaces.DIContainer;
import org.example.interfaces.MessageRepository;
import org.example.interfaces.MessageService;
import org.example.repositories.DatabaseMessageRepository;
import org.example.repositories.InMemoryMessageRepository;
import org.example.services.EmailService;
import org.example.services.SmsService;
import org.jboss.weld.environment.se.Weld;

public class App {
    public static void main(String[] args) {
        //In-memory message repository
        System.out.println("=== In-Memory Message Repository ===");
        MessageRepository messageRepository = new InMemoryMessageRepository();
        MessageService emailService = new EmailService(messageRepository);

        emailService.send(new Message("Hello", "Alfred"));

        System.out.println("--------------------------------");

        //Database message repository
        System.out.println("=== Database mMssage Repository ===");
        MessageRepository databaseRepository = new DatabaseMessageRepository();
        MessageService smsService = new SmsService(databaseRepository);

        smsService.send(new Message("Hello", "Alfred"));

        System.out.println("--------------------------------");

        //DI container
        System.out.println("=== Weld SE Container ===");
        Weld weld = new Weld();
        try (SeContainer container = weld.initialize()) {
            EmailService emailService2 = container.select(EmailService.class).get();
            emailService2.send(new Message("Hello", "Alfred"));
        }

        System.out.println("--------------------------------");
    }
}
