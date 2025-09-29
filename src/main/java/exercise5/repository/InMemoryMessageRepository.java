package exercise5.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;

@ApplicationScoped
public class InMemoryMessageRepository implements MessageRepository {
    List<String> messages;

    @Inject
    public InMemoryMessageRepository() {

    }
    @Override
    public void saveMessage(String message) {
    }
}
