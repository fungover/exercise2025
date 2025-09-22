package org.example.repositories;

public class FileRepository implements MessageRepository{
    @Override
    public void saveMessage(String message) {
        System.out.println("Saved in file " + message);
    }
}
