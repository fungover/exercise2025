package org.example.repository;

import org.example.domain.DataRepository;

/**
 * Another concrete implementation of DataRepository that simulates fetching data from a file.
 * - Does NOT know about the services that use it.
 */

public class FileRepository implements DataRepository {
    @Override
    public String getData() {
        return "Data from File"; // Simulated data retrieval
    }
}
