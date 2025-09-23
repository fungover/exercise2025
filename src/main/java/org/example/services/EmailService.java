package org.example.services;

import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.inject.Default;

@Dependent
@Default
public class EmailService implements MessageService {
    @Override
    public void sendMessage(String message) {
        System.out.println("EMAIL: " + message);
    }
}
