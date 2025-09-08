package org.example.service;

import org.example.entities.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Warehouse {
    private final List<Product> products = new ArrayList<>();

    // Getters
    public List<Product> getAllProducts() {
        return new ArrayList<>(products);
    }

    public Product getProductById(int id) {
        return products.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
    }

    // Methods
    public void addProduct(Product product) {
        if (product == null || product.getName().isBlank()) {
            throw new IllegalArgumentException("Product name cannot be null or blank");
        }
        products.add(product);
    }

    public void updateProduct(Product product) {
        if (product == null || product.getName().isBlank()) {
            throw new IllegalArgumentException("Product name cannot be null or blank");
        }

        int index = IntStream.range(0, products.size())
                .filter(i -> products.get(i).getId() == product.getId())
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        products.set(index, product);
    }

}
