package repository;

import entities.Product;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

// This class is ONLY responsible for storing and retrieving products.
public class InMemoryProductRepository implements ProductRepository {

    private final Map<String, Product> products = new ConcurrentHashMap<>();

    @Override
    public void save(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        if (product.id() == null || product.id().isBlank()) {
            throw new IllegalArgumentException("Product ID cannot be null or blank");
        }
        products.put(product.id(), product);
    }

    @Override
    public void addProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        if (product.id() == null || product.id().isBlank()) {
            throw new IllegalArgumentException("Product ID cannot be null or blank");
        }

        if (products.containsKey(product.id())) {
            throw new IllegalArgumentException("Product with ID " + product.id() + " already exists");
        }

        products.put(product.id(), product);
    }

    @Override
    public Optional<Product> getProductById(String id) {
        if (id == null || id.isBlank()) {
            return Optional.empty();
        }
        return Optional.ofNullable(products.get(id));
    }

    @Override
    public Optional<Product> findById(String id) {
        return getProductById(id);
    }

    @Override
    public List<Product> getAllProducts() {
        return products.values()
                .stream()
                .collect(Collectors.toList());
    }

    @Override
    public List<Product> findAll() {
        return getAllProducts();
    }

    @Override
    public void updateProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        if (product.id() == null || product.id().isBlank()) {
            throw new IllegalArgumentException("Product ID cannot be null or blank");
        }

        if (!products.containsKey(product.id())) {
            throw new IllegalArgumentException("Product with ID " + product.id() + " not found");
        }

        products.put(product.id(), product);
    }

    @Override
    public boolean removeProduct(String id) {
        if (id == null || id.isBlank()) {
            return false;
        }
        return products.remove(id) != null;
    }

    @Override
    public boolean existsById(String id) {
        if (id == null || id.isBlank()) {
            return false;
        }
        return products.containsKey(id);
    }

    @Override
    public long count() {
        return products.size();
    }

     // Helper method for debugging/testing
    // Debugging helper method - shows all stored IDs
    public void clear() {
        products.clear();
    }

    // Debugging helper method - shows all stored IDs
    public Set<String> getAllIds() {
        return new HashSet<>(products.keySet());
    }
}