package com.example.di.repository;

import jakarta.enterprise.context.ApplicationScoped;

import java.util.Arrays;
import java.util.List;

/**
 * Usage of the interface method, this is the easiest demo of my DI
 **/

@ApplicationScoped // one instance for the entire lifetime of the app
public class InMemoryProductRepository implements ProductRepository {
    @Override
    public List<String> findAll() {
        return Arrays.asList("Ronja", "Élan", "Mattias");
    }
}
