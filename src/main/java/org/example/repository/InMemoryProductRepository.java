package org.example.repository;

import org.example.entities.Product;

import java.util.*;

/**
 * InMemoryProductRepository - Concrete implementation of ProductRepository that stores products in memory.
 * <p>
 * - ABOUT THIS CLASS:
 * - Interface (ProductRepository) defines WHAT should be done (the contract)
 * - This class (InMemoryProductRepository) defines HOW it's done (Here we use HashMap for storage).
 * <p>
 * OTHER POSSIBLE IMPLEMENTATIONS:
 * - DatabaseProductRepository (If we would use something like an SQL Database)
 * - MockProductRepository (Could be used for tests, where we can return predefined data)
 */

public class InMemoryProductRepository implements ProductRepository {

    private final Map<String, Product> products = new HashMap<>(); // In-memory storage using a HashMap.

    /**
     * Here we implement the methods defined in the ProductRepository interface.
     */
    @Override
    public void addProduct(Product product) {
        Objects.requireNonNull(product, "Product cannot be null");
        String id = product.id();
        if (products.containsKey(id)) {
            throw new IllegalArgumentException("Product with id " + id + " already exists");
        }
        products.put(id, product);
    }

    @Override
    public Optional<Product> getProductById(String id) {
        return Optional.ofNullable(products.get(id)); // Retrieve product by ID, wrapped in Optional to handle "not found" case.
    }

    @Override
    public List<Product> getAllProducts() {
        return new ArrayList<>(products.values()); // Return all products as a list.
    }

    @Override
    public void updateProduct(Product product) {
        Objects.requireNonNull(product, "Product cannot be null"); // Null check for product
        if (!products.containsKey(product.id())) {
            throw new IllegalArgumentException("Product with id " + product.id() + " not found"); // If product doesn't exist, throw an exception.
        } else {
            products.put(product.id(), product); // Update the product in the map.
        }
    }
}
