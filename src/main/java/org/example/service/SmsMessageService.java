package org.example.service;

import org.example.domain.DataRepository;
import org.example.domain.MessageService;

/**
 * Another implementation of MessageService that processes SMS messages.
 * - Same pattern here: depends on DataRepository abstraction.
 * - Does NOT know about EmailMessageService or any other implementations.
 * - Final field to ensure immutability.
 */

public class SmsMessageService implements MessageService { // Implements MessageService interface
    private final DataRepository repository; // Dependency on DataRepository interface

    public SmsMessageService(DataRepository repository) { // Constructor injection
        this.repository = repository; // Assigning the injected repository to the final field
    }

    @Override
    public void processMessage() {
        String data = repository.getData(); // Using the injected dependency, does not know where data comes from.
        System.out.println("Sending SMS with: " + data); // Processing the message
    }
}
