package org.example.repository;

import org.example.entities.Product;
import java.util.*;

public class InMemoryProductRepository implements ProductRepository {

    private final Map<UUID, Product> products = new LinkedHashMap<>();

    private UUID parseUuid(String id) {
        try {
            return UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Product ID must be a valid UUID");
        }
    }
    @Override
    public void addProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        UUID productId = parseUuid(product.getId());

        if (products.containsKey(productId)) {
            throw new IllegalArgumentException("Product ID already exists");
        }

        products.put(productId, product);
    }

    @Override
    public List<Product> getAllProducts() {
        return List.copyOf(products.values());
    }

    @Override
    public Optional<Product> getProductById(String id) {
        try {
            UUID uuid = UUID.fromString(id);
            return Optional.ofNullable(products.get(uuid));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }

    @Override
    public void updateProduct(Product product) {
        UUID productId = parseUuid(product.getId());

        if (!products.containsKey(productId)) {
            throw new IllegalArgumentException("Product with ID " + product.getId() + " does not exist");
        }

        products.put(productId, product);
    }

}
