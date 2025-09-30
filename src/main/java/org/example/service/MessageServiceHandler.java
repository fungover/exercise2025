package org.example.service;

import org.example.repository.MessageRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class MessageServiceHandler implements MessageService {
    private final MessageRepository repository;

    @Inject
    public MessageServiceHandler(MessageRepository repository) {
        this.repository = repository;
    }

    @Override
    public void sendMessage(String message) {
        System.out.println("Skickar meddelande: " + message);
        repository.saveMessage(message);
    }
}
