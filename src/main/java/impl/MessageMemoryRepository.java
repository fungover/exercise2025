package impl;

import api.MessageRepository;
import jakarta.enterprise.context.Dependent;

@Dependent
public class MessageMemoryRepository implements MessageRepository {


    @Override
    public String messageFor(String name) {
        return "Hi, " + name;
    }
}
