package com.example.di;

/**
 * Service class that handles manual constructor injection
 **/

public class ProductService {
    private final ProductRepository repo;

    public ProductService(ProductRepository repo) {
        this.repo = repo;
    }

    public void printAll() {
        repo.findAll().forEach(System.out::println);
    }
}
