package org.example;

import org.example.repository.InMemoryProductRepository;
import org.example.repository.ProductRepository;
import org.example.service.ProductService;

public class Main {
    public static void main(String[] args) {
        ProductRepository repo = new InMemoryProductRepository();
        ProductService productService = new ProductService(repo);

    }
}
