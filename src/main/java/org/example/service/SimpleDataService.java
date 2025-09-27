package org.example.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.example.persistence.DataRepository;
import org.example.qualifiers.Simple;

@ApplicationScoped
@Simple
public class SimpleDataService implements DataService {
    private final DataRepository repository;

    // Constructor injection
    @Inject
    public SimpleDataService(DataRepository repository) {
        this.repository = repository;
    }
    @Override
    public void process(String data) {
        System.out.println("Processing: " + data);
        repository.save(data);
    }
}
