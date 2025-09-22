package org.example.persistence;

public class DatabaseRepository implements DataRepository {

    @Override
    public void save(String data) {
        System.out.println("Saving to database: " + data);
    }
}
