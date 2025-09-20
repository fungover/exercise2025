package org.example;

import org.example.repository.DataRepository;
import org.example.repository.DatabaseRepository;
import org.example.repository.FileRepository;
import org.example.service.EmailMessageService;
import org.example.service.MessageService;
import org.example.service.SmsMessageService;

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
    }
}
