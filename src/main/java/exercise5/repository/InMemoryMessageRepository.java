package exercise5.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
public class InMemoryMessageRepository implements MessageRepository {
    List<String> messages;

    @Inject
    public InMemoryMessageRepository(List<String> messages) {
        this.messages = messages;
    }
    @Override
    public void saveMessage(String message) {
        messages.add(message);
    }
}
