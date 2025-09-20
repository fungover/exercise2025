package org.example.service;

import org.example.domain.DataRepository;
import org.example.domain.MessageService;

public class SmsMessageService implements MessageService {
    private final DataRepository repository;

    public SmsMessageService(DataRepository repository) {
        this.repository = repository;
    }

    @Override
    public void processMessage() {
        String data = repository.getData();
        System.out.println("Sending SMS with: " + data);
    }
}
