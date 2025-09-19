package org.example.repository;

import org.example.entities.Product;

import java.util.*;

public class InMemoryProductRepository implements ProductRepository{
    private final List<Product> products = new ArrayList<>();

    @Override
    public void addProduct(Product product) {
        if (product == null) throw new IllegalArgumentException("Product must not be null");
        boolean exists = products.stream().anyMatch(p -> p.id().equals(product.id()));
        if (exists) throw new IllegalArgumentException("Product with ID " + product.id() + " already exists");
        products.add(product);
    }

    @Override
    public void updateProduct(Product product) {
        if (product == null) throw new IllegalArgumentException("Product must not be null");
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).id().equals(product.id())) {
                products.set(i, product);
                return;
            }
        }
        throw new NoSuchElementException("Product with ID " + product.id() + " not found");
    }

    @Override
    public List<Product> getAllProducts() {
        return Collections.unmodifiableList(products);
    }

    @Override
    public Optional<Product> getProductById(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("ID must not be null or empty");
        }
        return products.stream()
                .filter(p -> p.id().equals(id))
                .findFirst();
    }
}
