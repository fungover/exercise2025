package org.example.interfaces;

import org.example.entities.Message;

public interface MessageRepository {
    void save(Message message);
}
