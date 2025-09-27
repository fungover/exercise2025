package org.example.persistence;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Alternative;
import java.util.ArrayList;
import java.util.List;


@ApplicationScoped
@Alternative
public class InMemoryRepository implements DataRepository {
    private final List<String> storage = new ArrayList<>();

    @Override
    public void save(String data) {
        storage.add(data);
        System.out.println("Saved in memory: " + data);
    }
}
