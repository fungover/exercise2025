package org.example;

import org.example.container.Container;
import org.example.persistence.MessageRepository;
import org.example.persistence.implementation.SwedishMessageRepository;
import org.example.service.GreetingService;
import org.example.service.implementation.ConsoleGreetingService;

public class MiniMainContainer {
    public static void main(String[] args) {
        Container container = new Container();

        container.bind(MessageRepository.class, SwedishMessageRepository.class);
        container.bind(GreetingService.class, ConsoleGreetingService.class);

        App app = container.get(App.class);
        app.run();
    }
}
