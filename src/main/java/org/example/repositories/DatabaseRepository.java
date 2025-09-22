package org.example.repositories;

public class DatabaseRepository implements MessageRepository{
    @Override
    public void saveMessage(String message) {
        System.out.println("Message saved in database:  " + message);
    }
}
