package org.example.service;

import org.example.entities.Product;

import java.util.ArrayList;
import java.util.List;

public class Warehouse {
    private final List<Product> products = new ArrayList<>();

    public void addProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("product cannot be null");
        }

        boolean idExists = products.stream()
                .anyMatch(p -> p.id().equals(product.id()));
        if (idExists) {
            throw new IllegalArgumentException("product with id " + product.id() + " already exists");
        }

        products.add(product);
    }
    // Returnerar en kopia av lagret för att skydda intern lista
    public List<Product> getAllProducts() {
        return new ArrayList<>(products);
    }
}
