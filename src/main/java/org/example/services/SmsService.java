package org.example.services;

import org.example.entities.Message;
import org.example.interfaces.MessageRepository;
import org.example.interfaces.MessageService;

public class SmsService implements MessageService {
    private MessageRepository repository;

    public SmsService(MessageRepository repository) {
    this.repository = repository;
    }

    @Override
    public void send(Message message) {
    repository.save(message);
    System.out.println("SMS sent to " + message.sender());
    }
}
