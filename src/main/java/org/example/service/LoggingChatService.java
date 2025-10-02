package org.example.service;

import jakarta.enterprise.context.ApplicationScoped;

//For possible future implementation this could contain a logger. For now it just uses System.out.println

@LoggingChatServiceQualifier
@ApplicationScoped
public class LoggingChatService implements ChatService {
    @Override
    public void sendMessage(String message) {
        System.out.println("LoggingChatService.sendMessage: " + message);
    }
}