package org.example.repositories;

import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.inject.Alternative;

@Dependent
@Alternative
public class FileRepository implements MessageRepository {
    @Override
    public void saveMessage(String message) {
        System.out.println("FILE SAVE: " + message);
    }
}
