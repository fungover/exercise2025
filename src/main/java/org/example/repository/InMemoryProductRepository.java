package org.example.repository;

import org.example.entities.Product;

import java.util.*;

public class InMemoryProductRepository implements ProductRepository {

    private final Map<String, Product> store = new HashMap<>();

    public InMemoryProductRepository() { }

    @Override
    public List<Product> getAllProducts() {
        return List.copyOf(store.values());
    }

    @Override
    public void addProduct(Product product) {
        Objects.requireNonNull(product, "product");
        if (store.containsKey(product.id())) {
            throw new IllegalArgumentException("duplicate id: " + product.id());
        }
        store.put(product.id(), product);
    }

    @Override
    public Optional<Product> getProductById(String id) {
        Objects.requireNonNull(id, "id");
        if (id.isBlank()) throw new IllegalArgumentException("id required");
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public void updateProduct(Product product) {
        Objects.requireNonNull(product, "product");
        String id = product.id();
        if (!store.containsKey(id)) {
            throw new NoSuchElementException("not found: " + id);
        }
        store.put(id, product);
    }
}
