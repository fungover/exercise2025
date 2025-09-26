package org.example.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.example.domain.DataRepository;
import org.example.domain.MessageService;
import org.example.qualifiers.Sms;

/**
 * Another implementation of MessageService that processes SMS messages.
 * - Same pattern here: depends on DataRepository abstraction.
 * - Does NOT know about EmailMessageService or any other implementations.
 * - Final field to ensure immutability.
 */
@Sms // Same as in EmailMessageService but uses @Sms qualifier.
@ApplicationScoped
public class SmsMessageService implements MessageService { // Implements MessageService interface
    private final DataRepository repository; // Dependency on DataRepository interface

    @Inject
    public SmsMessageService(DataRepository repository) { // Constructor injection
        this.repository = repository; // Assigning the injected repository to the final field
    }

    @Override
    public void processMessage() {
        String data = repository.getData(); // Using the injected dependency, does not know where data comes from.
        System.out.println("Sending SMS with: " + data); // Processing the message
    }
}
