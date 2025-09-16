package org.example;

import org.example.repository.InMemoryProductRepository;
import org.example.service.ProductService;

import java.time.Clock;

public class App {
    public static void main(String[] args) {
        Clock clock = Clock.systemUTC();

        InMemoryProductRepository repository = new InMemoryProductRepository();
        ProductService productService = new ProductService(clock, repository);
    }
}
