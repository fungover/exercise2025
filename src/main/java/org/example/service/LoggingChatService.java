package org.example.service;

//For possible future implementation this could contain a logger. For now it just uses System.out.println

public class LoggingChatService implements ChatService {
    @Override
    public void sendMessage(String message) {
        System.out.println("LoggingChatService.sendMessage: " + message);
    }
}
