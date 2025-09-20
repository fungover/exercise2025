package org.example;

import org.example.di.SimpleDIContainer;
import org.example.entities.Message;
import org.example.interfaces.DIContainer;
import org.example.interfaces.MessageRepository;
import org.example.interfaces.MessageService;
import org.example.repositories.DatabaseMessageRepository;
import org.example.repositories.InMemoryMessageRepository;
import org.example.services.EmailService;
import org.example.services.SmsService;

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
        System.out.println("=== DI Container ===");
        DIContainer emailContainer = new SimpleDIContainer();
        emailContainer.register(MessageService.class, EmailService.class);
        emailContainer.register(MessageRepository.class, InMemoryMessageRepository.class);

        MessageService emailServiceDi = emailContainer.resolve(MessageService.class);
        emailServiceDi.send(new Message("Hello", "Alfred"));

        System.out.println("--------------------------------");
    }
}
