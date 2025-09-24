package org.example;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.example.repositories.MessageRepository;
import org.example.services.MessageService;

@ApplicationScoped
public class App {
    private final MessageService service;
    private final MessageRepository repository;

    @Inject
    public App(MessageService service, MessageRepository repository) {
        this.service = service;
        this.repository = repository;
    }

    public void processMessage(String msg) {
        service.sendMessage(msg);
        repository.saveMessage(msg);
    }
}
