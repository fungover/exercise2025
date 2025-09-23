package exercise5.part1.repository;

import exercise5.part1.enteties.Message;

import java.util.List;

public class InMemoryMessageRepository implements MessageRepository{
    List<String> messages;

    public InMemoryMessageRepository(List<String> messages) {
        this.messages = messages;
    }
    @Override
    public void saveMessage(String message) {
        messages.add(message);
    }
}
