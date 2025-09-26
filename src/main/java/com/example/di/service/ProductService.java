package com.example.di.service;

import com.example.di.repository.ProductRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

/**
 * Service class that handles manual constructor injection
 **/

@ApplicationScoped
public class ProductService implements ProductServiceInterface {
    private final ProductRepository repo;

    @Inject
    public ProductService(ProductRepository repo) {
        this.repo = repo;
    }

    @Override
    public void printAll() {
        repo.findAll().forEach(System.out::println);
    }
}
