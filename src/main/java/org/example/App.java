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
        System.out.println("=== Database Message Repository ===");
        MessageRepository databaseRepository = new DatabaseMessageRepository();
        MessageService smsService = new SmsService(databaseRepository);

        smsService.send(new Message("Hello", "Alfred"));

        System.out.println("--------------------------------");

        //Custom DI Container
        System.out.println("=== Custom DI Container ===");
        DIContainer container = new SimpleDIContainer();
        container.register(MessageService.class, EmailService.class);
        container.register(MessageRepository.class, InMemoryMessageRepository.class);
        MessageService emailServiceDi = container.resolve(MessageService.class);
        emailServiceDi.send(new Message("Hello", "Alfred"));

        System.out.println("--------------------------------");

        //Weld container
        System.out.println("=== Weld SE Container ===");
        Weld weld = new Weld();
        try (SeContainer weldContainer = weld.initialize()) {
            EmailService emailService2 = weldContainer.select(EmailService.class).get();
            emailService2.send(new Message("Hello", "Alfred"));
        }

        System.out.println("--------------------------------");
    }
}
