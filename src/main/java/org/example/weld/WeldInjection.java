package org.example.weld;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import org.example.service.ChatService;
import org.example.service.MainChatServiceQualifier;

@ApplicationScoped
public class WeldInjection {

    @Inject
    @MainChatServiceQualifier

    private ChatService chatService;

    public void run() {
        chatService.sendMessage("Hello! This is a message using DI with Weld CDI!");
    }
}