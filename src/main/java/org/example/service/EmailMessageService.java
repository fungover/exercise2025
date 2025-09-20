package org.example.service;

import org.example.domain.DataRepository;
import org.example.domain.MessageService;

/**
 * DEPENDENCY INJECTION:
 * - Receives DataRepository from constructor injection.
 * - Does NOT know which repository which is being used (Database or File).
 * - Final field to ensure immutability.
 */

public class EmailMessageService implements MessageService {
    private final DataRepository repository; // Dependency on DataRepository interface

    public EmailMessageService(DataRepository repository) { // Constructor injection
        this.repository = repository; // Assigning the injected repository to the final field
    }

    @Override
    public void processMessage() {
        String data = repository.getData(); // Using the injected dependency, does not know where data comes from.
        System.out.println("Sending email with: " + data); // Processing the message
    }
}
