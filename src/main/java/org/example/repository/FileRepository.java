package org.example.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Alternative;
import org.example.domain.DataRepository;

/**
 * Another concrete implementation of DataRepository that simulates fetching data from a file.
 * - Does NOT know about the services that use it.
 */

@ApplicationScoped
@Alternative
public class FileRepository implements DataRepository {
    @Override
    public String getData() {
        return "Data from File"; // Simulated data retrieval
    }
}
