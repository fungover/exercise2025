package service;

import entities.Category;
import entities.Product;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

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

    // Sorted A-Ö, filters products from the same category that I send in. Is returned in alphabetical order, immutable copy
    public List<Product> getProductsByCategorySorted(Category category) {
        Objects.requireNonNull(category, "category can not be null");
        return productById.values().stream().filter(p -> p.category() == category)
                .sorted(Comparator.comparing(Product::name, String.CASE_INSENSITIVE_ORDER))
                .collect(Collectors.collectingAndThen(Collectors.toList(), Collections::unmodifiableList));
    }

    // "Created at what date" - gets and filters through all product and returns which ones have a specific createdDate
    public List<Product> getProductsCreatedAfter(LocalDate date) {
        Objects.requireNonNull(date, "date can not be null");
        return productById.values().stream()
                .filter(p -> p.createdDate().isAfter(date))
                .collect(Collectors.collectingAndThen(Collectors.toList(), Collections::unmodifiableList));
    }

    // Gets all products, filters all products that are changed after creation
    public List<Product> getModifiedProducts() {
        return productById.values().stream().filter(p -> !p.createdDate().equals(p.modifiedDate()))
                .collect(Collectors.collectingAndThen(Collectors.toList(), Collections::unmodifiableList));
    }

}

