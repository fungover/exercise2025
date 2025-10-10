package org.example.repository;

import org.example.entities.Product;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class InMemoryProductRepository implements ProductRepository {
    private final Map<String, Product> productMap = new ConcurrentHashMap<>();

    @Override
    public void addProduct(Product product) {
        productMap.put(product.getId(), product);
    }

    @Override
    public Optional<Product> getProductById(String id) {
        return Optional.ofNullable(productMap.get(id));
    }

    @Override
    public List<Product> getAllProducts() {
        return new ArrayList<>(productMap.values());
    }

    @Override
    public void updateProduct(Product product) {
        productMap.put(product.getId(), product);
    }
}
