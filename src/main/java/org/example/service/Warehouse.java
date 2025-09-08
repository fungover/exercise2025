package org.example.service;

import org.example.entities.Product;

import java.util.*;
import java.util.stream.Collectors;

public class Warehouse {

    private final Map<UUID, Product> products = new LinkedHashMap<>();

    public void addProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }

        UUID productId;
        try {
            productId = UUID.fromString(product.id());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Product ID must be a valid UUID");
        }

        if (products.containsKey(productId)) {
            throw new IllegalArgumentException("Product ID already exists");
        }

        products.put(productId, product);
    }


    public List<Product> getAllProducts() {
        return products.values()
                .stream()
                .collect(Collectors.toUnmodifiableList());
    }
}
