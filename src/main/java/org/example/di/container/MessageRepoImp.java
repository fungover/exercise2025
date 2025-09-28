package org.example.di.container;

import org.example.di.common.MessageRepository;

public class MessageRepoImp implements MessageRepository {
    public MessageRepoImp() {
    }
    @Override
    public String getMessage() {
        return "Hi";
    }
}
