package org.example;

import org.example.repositories.MessageRepository;
import org.example.services.MessageService;

public class App {
    private final MessageService service;
    private final MessageRepository repository;

    // Injection
    public App(MessageService messageService, MessageRepository messageRepository) {
        this.service = messageService;
        this.repository = messageRepository;
    }

    public void processMessage(String message) {
        service.sendMessage(message);
        repository.saveMessage(message);
    }
}
