package com.example.di;

import java.util.Arrays;
import java.util.List;

/**
 * Usage of the interface method
 **/

public class InMemoryProductRepository implements ProductRepository {
    @Override
    public List<String> findAll() {
        return Arrays.asList("Ronja", "Élan", "Mattias");
    }
}
