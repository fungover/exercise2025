package org.example.Repository;

import org.example.entities.Product;

import java.util.*;

/**
 * In-memory implementation of ProductRepository.
 * Stores products in a HashMap for quick access by ID.
 */

public class InMemoryProductRepository implements ProductRepository {

    private final Map<String, Product> productMap = new HashMap<>();

    @Override
    public void addProduct(Product product) {
        if (productMap.containsKey(product.getId())) {
            throw new IllegalArgumentException("Product with ID " + product.getId() + " already exists");
        }
        productMap.put(product.getId(), product);
    }

    @Override
    public Optional<Product> getProduct(String id) {
        return Optional.ofNullable(productMap.get(id));
    }

    @Override
    public List<Product> getAllProducts() {
        return new ArrayList<>(productMap.values());
    }

    @Override
    public void updateProduct(Product product) {
        if (!productMap.containsKey(product.getId())) {
            throw new IllegalArgumentException("Product with ID " + product.getId() + " not found");
        }
        productMap.put(product.getId(), product);
    }
}