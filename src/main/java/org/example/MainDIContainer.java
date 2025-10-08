package org.example;

import org.example.DIContainer.DIContainer;
import org.example.service.ChatService;

public class MainDIContainer {
    public static void main(String[] args) {
        DIContainer container = new DIContainer();
        ChatService service = container.getInstance(ChatService.class);

        service.sendMessage("Hello! This is a message using DI container.");
    }
}
