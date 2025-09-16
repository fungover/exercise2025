package org.example.repository;

import org.example.entities.Product;

import java.util.*;

public class InMemoryProductRepository implements ProductRepository {

    private final Map<String, Product> products = new HashMap<>();

    @Override
    public void addProduct(Product product) {
        Objects.requireNonNull(product, "Product cannot be null");
        products.put(product.id(), product);
    }

    @Override
    public Optional<Product> getProductById(String id) {
        return Optional.ofNullable(products.get(id));
    }

    @Override
    public List<Product> getAllProducts() {
        return new ArrayList<>(products.values());
    }

    @Override
    public void updateProduct(Product product) {

        Objects.requireNonNull(product, "Product cannot be null");
        if (!products.containsKey(product.id())) {
            throw new IllegalArgumentException("Product with id " + product.id() + " not found");
        } else {
            products.put(product.id(), product);
        }
    }
}
