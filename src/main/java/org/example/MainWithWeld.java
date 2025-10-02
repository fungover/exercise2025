package org.example;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import jakarta.inject.Inject;

import org.example.service.ChatService;
import org.example.service.LoggingChatServiceQualifier;
import org.example.service.MainChatServiceQualifier;

@ApplicationScoped
public class MainWithWeld {

    //Choose what dependency you want by either commenting out MainChatServiceQualifier or LoggingChatServiceQualifier
    @Inject
    @MainChatServiceQualifier
    //@LoggingChatServiceQualifier

    private ChatService chatService;

    public static void main(String[] args) {
        try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {
            MainWithWeld mw = container.select(MainWithWeld.class).get();
            mw.run();
        }
    }

    private void run() {
        chatService.sendMessage("Hello! This is a message using DI with Weld CDI!");
    }
}