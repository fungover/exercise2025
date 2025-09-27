package org.example.repository;

import org.example.entities.Product;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryProductRepository implements ProductRepository {

    private final Map<String, Product> products;

    public InMemoryProductRepository() {
        this.products = new ConcurrentHashMap<>();
    }

    @Override
    public void addProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        if (product.id() == null || product.id().trim().isEmpty()) {
            throw new IllegalArgumentException("Product ID cannot be empty");
        }
        if (product.name() == null || product.name().trim().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be empty");
        }

        Product previous = products.putIfAbsent(product.id(), product);
        if (previous != null) {
            throw new IllegalArgumentException("Product with ID " + product.id() + " already exists");
        }
    }

    @Override
    public Optional<Product> getProductById(String id) {
        if (id == null || id.trim().isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(products.get(id));
    }

    @Override
    public List<Product> getAllProducts() {
        return new ArrayList<>(products.values());
    }

    @Override
    public void updateProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        if (product.id() == null || product.id().trim().isEmpty()) {
            throw new IllegalArgumentException("Product ID cannot be empty");
        }
        if (product.name() == null || product.name().trim().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be empty");
        }

        products.compute(product.id(), (key, existing) -> {
            if (existing == null) {
                throw new IllegalArgumentException("Product with ID " + product.id() + " not found");
            }
            return product;
        });
    }
}
