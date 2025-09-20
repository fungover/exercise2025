package org.example.service;

import org.example.repository.DataRepository;

public class EmailMessageService implements MessageService {
    private final DataRepository repository;

    public EmailMessageService(DataRepository repository) {
        this.repository = repository;
    }

    @Override
    public void processMessage() {
        String data = repository.getData();
        System.out.println("Sending email with: " + data);
    }
}
