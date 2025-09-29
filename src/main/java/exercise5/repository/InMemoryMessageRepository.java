package exercise5.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@ApplicationScoped
public class InMemoryMessageRepository implements MessageRepository {
    private final List<String> messages;

    @Inject
    public InMemoryMessageRepository() {
        this.messages = new CopyOnWriteArrayList<>();
    }
    @Override
    public void saveMessage(String message) {
        messages.add(message);
    }
}
