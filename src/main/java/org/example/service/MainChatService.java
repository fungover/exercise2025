package org.example.service;
import jakarta.enterprise.context.ApplicationScoped;

//For future implementation this could also have other methods mentioned in the ChatService interface.
@ApplicationScoped
@MainChatServiceQualifier
public class MainChatService implements ChatService {
    @Override
    public void sendMessage(String message) {
        System.out.println("the message is:" + message);
    }
}