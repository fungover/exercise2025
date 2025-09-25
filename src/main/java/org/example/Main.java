package org.example;

// This is the main class where the program starts

import org.example.repository.MessageRepository;
import org.example.repository.MessageRepositoryHandler;
import org.example.service.MessageService;
import org.example.service.MessageServiceHandler;

public class Main {
    public static void main(String[] args) {
        // Create a repository to handle messages
        MessageRepository repository = new MessageRepositoryHandler();

        // Create a service and give it the repository
        MessageService service = new MessageServiceHandler(repository);

        // Use the service to send a message
        service.sendMessage("Hej hur mår du?");
    }
}

