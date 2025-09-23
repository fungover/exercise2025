package org.example.repository;

import jakarta.enterprise.context.ApplicationScoped;
import org.example.domain.DataRepository;

/**
 * Concrete implementation of DataRepository that simulates fetching data from a database.
 * - Does NOT know about the services that use it.
 */
@ApplicationScoped
public class DatabaseRepository implements DataRepository {

    @Override
    public String getData() {
        return "Data from Database"; // Simulated data retrieval
    }
}
