package org.example.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.example.entities.Message;
import org.example.interfaces.MessageRepository;
import org.example.interfaces.MessageService;
import org.example.qualifiers.Database;

@ApplicationScoped
public class SmsService implements MessageService {
    private MessageRepository repository;

    @Inject
    public SmsService(@Database MessageRepository repository) {
    this.repository = repository;
    }

    @Override
    public void send(Message message) {
    repository.save(message);
    System.out.println("SMS sent to " + message.sender());
    }
}
