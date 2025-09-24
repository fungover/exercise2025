package org.example.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.example.persistence.PersistenceLayer;
import org.example.qualifiers.DatabaseStore;

@ApplicationScoped
public class AdvancedService implements Service {
    private final PersistenceLayer persistence;

    // change between @FileStore and @DatabaseStore to test different implementations
    @Inject
    public AdvancedService(@DatabaseStore PersistenceLayer persistence) {
        this.persistence = persistence;
    }

    public void save (String data){
        persistence.save(data);
    }

    public void update (String data){
        persistence.update(data);
    }

    public void delete (String filename){
        persistence.delete(filename);
    }

    @Override
    public void processData(String data) {
        System.out.println("Processing data: " + data);
    }

}
