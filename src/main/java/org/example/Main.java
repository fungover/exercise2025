package org.example;

import org.example.repository.InMemoryMessageRepository;
import org.example.repository.MessageRepository;
import org.example.repository.MessageRepositoryHandler;
import org.example.service.MessageService;
import org.example.service.MessageServiceHandler;

public class Main {
    public static void main(String[] args) {

        // Create repositories to handle messages
        MessageRepository repository = new MessageRepositoryHandler();
        MessageRepository repository2 = new InMemoryMessageRepository();

        // Create a service and give it the repository
        MessageService service = new MessageServiceHandler(repository);
        MessageService service2 = new MessageServiceHandler(repository2);

        // Use the service to send message
        service.sendMessage("Hej hur mår du?");
        service2.sendMessage("Hej, detta meddelande går till listan");
    }
}

