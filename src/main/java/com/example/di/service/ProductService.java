package com.example.di.service;

import com.example.di.repository.ProductRepository;

/**
 * Service class that handles manual constructor injection
 **/

public class ProductService implements ProductServiceInterface {
    private final ProductRepository repo;

    public ProductService(ProductRepository repo) {
        this.repo = repo;
    }

    public void printAll() {
        repo.findAll().forEach(System.out::println);
    }
}
