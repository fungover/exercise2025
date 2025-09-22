package org.example;

import org.example.repositories.DatabaseRepository;
import org.example.repositories.MessageRepository;
import org.example.services.EmailService;
import org.example.services.MessageService;

public class Main {
    public static void main(String[] args) {

        MessageService service = new EmailService();
        MessageRepository repository = new DatabaseRepository();

        App app = new App(service, repository);

        app.processMessage("Dependency Injection is happening :)");
    }
}
