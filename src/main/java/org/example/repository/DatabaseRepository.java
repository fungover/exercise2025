package org.example.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Default;
import org.example.domain.DataRepository;

/**
 * Concrete implementation of DataRepository that simulates fetching data from a database.
 * - Does NOT know about the services that use it.
 */

@ApplicationScoped
@Default
public class DatabaseRepository implements DataRepository {

    @Override
    public String getData() {
        return "Data from Database"; // Simulated data retrieval
    }
}
