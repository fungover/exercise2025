package org.example.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Alternative;
import jakarta.inject.Inject;
import org.example.persistence.DataRepository;

@ApplicationScoped
@Alternative
public class AdvancedDataService implements DataService {
    private final DataRepository dataRepository;

    @Inject
    public AdvancedDataService(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }
    @Override
    public void process(String data) {
        String enriched = "[ADVANCED] " + data.toUpperCase();
        System.out.println("AdvancedDataService processing: " + enriched);
        dataRepository.save(enriched);

    }
}
