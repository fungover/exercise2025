package org.example;

import org.example.container.SimpleContainer;
import org.example.domain.DataRepository;
import org.example.domain.MessageService;
import org.example.repository.DatabaseRepository;
import org.example.service.EmailMessageService;

public class ContainerApp {

    public static void main(String[] args) {

        System.out.println("=== Testing Simple container with Reflection ===");

        System.out.println("Test 1: EmailService + DatabaseRepository");
        SimpleContainer container1 = new SimpleContainer();
        container1.bind(DataRepository.class, DatabaseRepository.class);
        container1.bind(MessageService.class, EmailMessageService.class);

        MessageService service1 = container1.getInstance(MessageService.class);
        service1.processMessage();
    }
}
