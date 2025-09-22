package org.example.service;

import org.example.persistence.DataRepository;

public class SimpleDataService implements DataService {

    private final DataRepository repository;

    // Constructor injection
    public SimpleDataService(DataRepository repository) {
        this.repository = repository;
    }
    @Override
    public void process(String data) {
        System.out.println("Processing: " + data);
        repository.save(data);
    }
}
