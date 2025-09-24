package org.example.repository;

import jakarta.enterprise.inject.Vetoed;
import org.example.domain.DataRepository;

/**
 * Another concrete implementation of DataRepository that simulates fetching data from a file.
 * - Does NOT know about the services that use it.
 */
@Vetoed // Using Vetoed here so that FilerRepository is ignored by Weld. (Could use qualifiers here tho!)
public class FileRepository implements DataRepository {
    @Override
    public String getData() {
        return "Data from File"; // Simulated data retrieval
    }
}
