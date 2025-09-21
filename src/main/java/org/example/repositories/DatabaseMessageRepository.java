package org.example.repositories;

import jakarta.enterprise.context.Dependent;
import org.example.entities.Message;
import org.example.interfaces.MessageRepository;
import org.example.qualifiers.Database;

import java.util.ArrayList;
import java.util.List;

@Dependent
@Database
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
