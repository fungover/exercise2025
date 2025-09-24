package org.example.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.example.entities.Message;
import org.example.interfaces.MessageRepository;
import org.example.interfaces.MessageService;
import org.example.qualifiers.InMemory;

@ApplicationScoped
public class EmailService implements MessageService {
    private MessageRepository repository;

    @Inject
    public EmailService(@InMemory MessageRepository repository) {
        this.repository = repository;
    }

    @Override
    public void send(Message message) {
        repository.save(message);
        System.out.println("Email sent to " + message.sender());
    }
}
