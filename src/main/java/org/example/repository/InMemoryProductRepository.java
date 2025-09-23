package org.example.repository;

import org.example.entities.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InMemoryProductRepository implements ProductRepository {
    private final List<Product> products = new ArrayList<>();

    @Override
    public void addProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        products.add(product);
    }

    @Override
    public Optional<Product> getProductById(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Product id cannot be empty");
        }
        return products.stream()
                .filter(product -> product.id().equals(id))
                .findFirst();
    }

    @Override
    public List<Product> getAllProducts() {
        return new ArrayList<>(products);
    }

    @Override
    public void updateProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        products.removeIf(p -> p.id().equals(product.id()));
        products.add(product);
    }

    @Override
    public void removeProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        products.removeIf(p -> p.id().equals(product.id()));
    }
}
