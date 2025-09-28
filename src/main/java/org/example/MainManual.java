package org.example;

import org.example.persistence.MessageRepository;
import org.example.persistence.implementation.EnglishMessageRepository;
import org.example.persistence.implementation.SwedishMessageRepository;
import org.example.service.GreetingService;
import org.example.service.implementation.ConsoleGreetingService;
import org.example.service.implementation.LoggerGreetingService;

public class MainManual {
    public static void main(String[] args) {
        MessageRepository repoSv = new SwedishMessageRepository();
        GreetingService serviceConsole = new ConsoleGreetingService(repoSv);
        serviceConsole.greet("Johannes");

        MessageRepository repoEn = new EnglishMessageRepository();
        GreetingService serviceLogger = new LoggerGreetingService(repoEn);
        serviceLogger.greet("Johannes");
    }
}
