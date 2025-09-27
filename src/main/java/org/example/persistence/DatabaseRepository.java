package org.example.persistence;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DatabaseRepository implements DataRepository {

    @Override
    public void save(String data) {
        System.out.println("Saving to database: " + data);
    }
}
