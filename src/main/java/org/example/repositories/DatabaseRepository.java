package org.example.repositories;

import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.inject.Default;

@Dependent
@Default
public class DatabaseRepository implements MessageRepository {
    @Override
    public void saveMessage(String message) {
        System.out.println("DB SAVE: " + message);
    }
}
