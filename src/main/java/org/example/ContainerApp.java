package org.example;

import org.example.container.SimpleContainer;
import org.example.domain.DataRepository;
import org.example.domain.MessageService;
import org.example.repository.DatabaseRepository;
import org.example.repository.FileRepository;
import org.example.service.EmailMessageService;
import org.example.service.SmsMessageService;

public class ContainerApp {

    public static void main(String[] args) {

        System.out.println("=== Testing Simple container with Reflection ===");

// === Test 1: EmailService + DatabaseRepository ===
        SimpleContainer container = new SimpleContainer();

// Configures the container - tells which implementation to use.
// "When someone asks for DataRepository, give them DatabaseRepository".
        container.bind(DataRepository.class, DatabaseRepository.class);
// "When someone asks for MessageService, give them EmailMessageService".
        container.bind(MessageService.class, EmailMessageService.class);

        MessageService service1 = container.getInstance(MessageService.class);
        service1.processMessage();

// === Test 2: SmsService + FileRepository ===
// Rebind dependencies to simulate a new configuration
        container.bind(DataRepository.class, FileRepository.class);
        container.bind(MessageService.class, SmsMessageService.class);

        MessageService service2 = container.getInstance(MessageService.class);
        service2.processMessage();


    }
}
