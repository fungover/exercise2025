package org.example;

import org.example.domain.DataRepository;
import org.example.repository.DatabaseRepository;
import org.example.repository.FileRepository;
import org.example.service.EmailMessageService;
import org.example.domain.MessageService;
import org.example.service.SmsMessageService;

/**
 * MANUAL DEPENDENCY INJECTION
 * - Here all the "wiring" is done manually.
 * - We choose which implementation that we want to use.
 * - This is the only place where we create objects and wire dependencies.
 */

public class App {
    public static void main(String[] args) {

        System.out.println("=== Manual Dependency Injection ===");


        // Email service with database repository.
        DataRepository dbRepository = new DatabaseRepository();
        MessageService emailService = new EmailMessageService(dbRepository);
        emailService.processMessage();

        // SMS service with file repository.
        DataRepository fileRepository = new FileRepository();
        MessageService smsService = new SmsMessageService(fileRepository);
        smsService.processMessage();

        // Email service with file repository.
        MessageService emailWithFileService = new EmailMessageService(fileRepository);
        emailWithFileService.processMessage();

        MessageService testService = new SmsMessageService(dbRepository);
        testService.processMessage();
    }
}
