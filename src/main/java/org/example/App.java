package org.example;

import service.InMemoryProductRepository;

import service.ProductService;


public class App {
    public static void main(String[] args) {
        InMemoryProductRepository inMemoryProductRepository = new InMemoryProductRepository();
        ProductService productService = new ProductService(inMemoryProductRepository);
    }
}
