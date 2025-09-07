package org.example.service;

import org.example.entities.Product;

import java.util.ArrayList;
import java.util.List;

public class Warehouse {
    private final List<Product> products = new ArrayList<>();

    // Getters
    public List<Product> getAllProducts() {
        return new ArrayList<>(products);
    }

    // Methods
    public void addProduct(Product product) {
        if (product == null || product.getName().isBlank()) {
            throw new IllegalArgumentException("Product name cannot be null or blank");
        }
        products.add(product);
    }

}
