package com.jan_elia.warehouse.repository;

import com.jan_elia.warehouse.entities.Product;

import java.util.*;

// In-memory implementation of ProductRepository
public class InMemoryProductRepository implements ProductRepository {

    // internal storage
    private final Map<String, Product> products = new HashMap<>();

    @Override
    public void addProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("product cannot be null");
        }
        if (products.containsKey(product.getId())) {
            throw new IllegalArgumentException("id already exists");
        }
        products.put(product.getId(), product);
    }

    @Override
    public Optional<Product> getProductById(String id) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("id cannot be null or blank");
        }
        return Optional.ofNullable(products.get(id));
    }

    @Override
    public List<Product> getAllProducts() {
        // return a defensive copy
        return Collections.unmodifiableList(new ArrayList<>(products.values()));
    }

    @Override
    public void updateProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("product cannot be null");
        }
        if (!products.containsKey(product.getId())) {
            throw new NoSuchElementException("product not found");
        }
        products.put(product.getId(), product);
    }
}
