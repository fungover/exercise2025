package org.example.repository;

import org.example.domain.DataRepository;

public class DatabaseRepository implements DataRepository {

    @Override
    public String getData() {
        return "Data from Database";
    }
}
