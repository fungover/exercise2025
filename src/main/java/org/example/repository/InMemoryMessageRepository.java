package org.example.repository;

import java.util.ArrayList;
import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class InMemoryMessageRepository implements  MessageRepository {
    private final List<String> messages = new ArrayList<>();

    @Override
    public void saveMessage(String message) {
        messages.add(message);
        System.out.println("Meddelandet sparades i listan!");
    }

    public List<String> getMessages() {
        return messages;
    }

}
