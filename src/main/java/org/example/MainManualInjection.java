package org.example;

import org.example.service.*;

/*
Run this to verify that it works with manual DI
 */
public class MainManualInjection {
    public static void main(String[] args) {
        ChatService chatMessage = new MainChatService();
        ChatService loggingMessage = new LoggingChatService();

        chatMessage.sendMessage("Testing! Chat service");
        loggingMessage.sendMessage("Testing! LoggingChat service");
    }
}
