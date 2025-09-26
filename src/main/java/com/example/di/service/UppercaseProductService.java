package com.example.di.service;

import com.example.di.repository.ProductRepository;

public class UppercaseProductService implements ProductServiceInterface {
    private final ProductRepository repo;

    public UppercaseProductService(ProductRepository repo) {
        this.repo = repo;
    }

    @Override
    public void printAll() {
        repo.findAll().forEach(p -> System.out.println(p.toUpperCase()));
    }
}
