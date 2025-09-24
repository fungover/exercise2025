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

        System.out.println("Test 1: EmailService + DatabaseRepository");


        SimpleContainer container1 = new SimpleContainer(); // Creates a mew container class.
        SimpleContainer container2 = new SimpleContainer();

        // Configures the container - tells which implementation to use.
        // "When someone asks for DataRepository, give them DatabaseRepository".
        container1.bind(DataRepository.class, DatabaseRepository.class);
        // "When someone asks for MessageService, give them EmailMessageService".
        container1.bind(MessageService.class, EmailMessageService.class);

        container2.bind(DataRepository.class, FileRepository.class);
        container2.bind(MessageService.class, SmsMessageService.class);

        /**
         * Here, we only ask for MessageService - nothing else.
         * The container will automatically:
         * 1. See that MessageService -> EmailMessageService.
         * 2. See that EmailMessageService needs DataRepository in the constructor.
         * 3. See that DataRepository -> DatabaseRepository.
         * 4. Create a DatabaseRepository first (no dependencies).
         * 5. Use DatabaseRepository to create EmailMessageService.
         * 6. Return the fully created EmailMessageService as MessageService.
         */

        System.out.println("=== ");

        MessageService service1 = container1.getInstance(MessageService.class);
        service1.processMessage();

        MessageService service2 = container2.getInstance(MessageService.class);
        service2.processMessage();


    }
}
