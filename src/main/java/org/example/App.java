package org.example;

import org.example.repository.InMemoryProductRepository;
import org.example.service.ProductService;

public class App {
    public static void main(String[] args) {
        InMemoryProductRepository productRepository = new InMemoryProductRepository();
        ProductService productService = new ProductService(productRepository);
    }
}
