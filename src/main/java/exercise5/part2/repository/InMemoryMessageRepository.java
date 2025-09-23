package exercise5.part2.repository;

import java.util.List;

public class InMemoryMessageRepository implements MessageRepository {
    List<String> messages;

    public InMemoryMessageRepository(List<String> messages) {
        this.messages = messages;
    }
    @Override
    public void saveMessage(String message) {
        messages.add(message);
    }
}
