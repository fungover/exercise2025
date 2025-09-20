package org.example.repositories;

import org.example.entities.Message;
import org.example.interfaces.MessageRepository;

import java.util.ArrayList;
import java.util.List;

public class DatabaseMessageRepository implements MessageRepository {
    private List<Message> messages = new ArrayList<>();

    @Override
    public void save(Message message) {
        if (message != null) {
            messages.add(message);
            System.out.println("Saving message: \"" + message.message() + "\" from sender " + message.sender() + " to database.");

        }
    }
}
