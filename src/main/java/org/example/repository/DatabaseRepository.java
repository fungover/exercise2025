package org.example.repository;

public class DatabaseRepository implements DataRepository {

    @Override
    public String getData() {
        return "Data from Database";
    }
}
