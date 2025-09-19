package org.example;

import org.example.repository.InMemoryProductRepository;
import org.example.repository.ProductRepository;
import org.example.service.ProductService;

public class App {
    public static void main(String[] args) {
        // Create repository
        ProductRepository repo = new InMemoryProductRepository();
        //Create service and inject repository
        ProductService productService = new ProductService(repo);

        System.out.println("Antal produkter: " + productService.getAllProducts().size());
    }
}
