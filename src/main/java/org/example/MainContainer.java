package org.example;

import org.example.container.Container;
import org.example.service.MessageService;

public class MainContainer {
    static void main(String[] args) {
        Container container = new Container();

        MessageService service = container.getInstance(MessageService.class);

        service.sendMessage("Hej från DI container!");
    }
}
