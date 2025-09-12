package entities;

import java.time.LocalDate;
import java.util.*;

public class Warehouse {

    private final Map<String, Product> productById = new HashMap<>();

    public Product addProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("product can not be null");
        }

        if (productById.containsKey(product.id())) {
            throw new IllegalArgumentException("Product already exists: " + product.id());
        }
        productById.put(product.id(), product);
        return product;
    }

    public Product getProductById(String id) {
        Objects.requireNonNull(id, "Id can not be null");
        Product p = productById.get(id);
        if (p == null) {
            throw new IllegalArgumentException("No product with id: " + id);
        }
        return p;
    }

    public List<Product> getAllProducts() {
        return List.copyOf(productById.values());
    }

    public Product updateProduct(String id, String name, Category category, int rating) {
        Objects.requireNonNull(id, "Id can not be null");
        Objects.requireNonNull(category, "category can not be null");

        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name can not be empty");
        }
        if (rating < 0 || rating > 10) {
            throw new IllegalArgumentException("Rating must be between 0 and 10");
        }

        Product existing = productById.get(id);
        if (existing == null) {
            throw new IllegalArgumentException("No products with id: " + id);
        }

        LocalDate now = LocalDate.now();

        Product updated = new Product(
                existing.id(),
                name.trim(),
                category,
                rating,
                existing.createdDate(),
                now
        );

        productById.put(id, updated);
        return updated;
    }

}

