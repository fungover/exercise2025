package org.example.repositories;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Qualifier;
import org.example.entities.Message;
import org.example.interfaces.MessageRepository;
import org.example.qualifiers.InMemory;

import java.util.ArrayList;
import java.util.List;

@Dependent
@InMemory
public class InMemoryMessageRepository implements MessageRepository {
    private List<Message> messages = new ArrayList<>();

    @Override
    public void save(Message message) {
        if (message != null) {
            messages.add(message);
            System.out.println("Saving message: \"" + message.message() + "\" from sender " + message.sender() + " to in-memory database.");
        }
    }
}
