package org.example.service;

import org.example.repository.MessageRepository;

public class MessageServiceHandler implements MessageService {
    private final MessageRepository repository;

    public MessageServiceHandler(MessageRepository repository) {
        this.repository = repository;
    }

    @Override
    public void sendMessage(String message) {
        System.out.println("Skickar meddelande: " + message);
        repository.saveMessage(message);
    }

}
