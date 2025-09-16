package com.jan_elia.warehouse.service;

import com.jan_elia.warehouse.entities.Category;
import com.jan_elia.warehouse.entities.Product;
import com.jan_elia.warehouse.repository.ProductRepository;

import java.time.Clock;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

// Renamed from Warehouse to ProductService
public class ProductService {

    private final Clock clock;
    private final ProductRepository repository;

    public ProductService(Clock clock, ProductRepository repository) {
        this.clock = clock;
        this.repository = repository;
    }

    // addProduct
    public void addProduct(Product product) {
        validateProduct(product);
        repository.addProduct(product);
    }

    // updateProduct
    public void updateProduct(String id, String name, Category category, int rating) {
        validateId(id);
        validateName(name);
        validateCategory(category);
        validateRating(rating);

        Product old = repository.getProductById(id)
                .orElseThrow(() -> new NoSuchElementException("product not found"));

        Product updated = new Product.Builder()
                .id(old.getId())
                .name(name)
                .category(category)
                .rating(rating)
                .createdDate(old.getCreatedDate())
                .modifiedDate(LocalDate.now(clock))
                .build();

        repository.updateProduct(updated);
    }

    // getAllProducts
    public List<Product> getAllProducts() {
        return repository.getAllProducts();
    }

    // getProductById
    public Product getProductById(String id) {
        validateId(id);
        return repository.getProductById(id)
                .orElseThrow(() -> new NoSuchElementException("product not found"));
    }

    // getProductsByCategorySorted
    public List<Product> getProductsByCategorySorted(Category category) {
        validateCategory(category);
        return repository.getAllProducts().stream()
                .filter(p -> p.getCategory().equals(category))
                .sorted(Comparator.comparing(p -> p.getName().toLowerCase(Locale.ROOT)))
                .collect(Collectors.toUnmodifiableList());
    }

    // getProductsCreatedAfter
    public List<Product> getProductsCreatedAfter(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("date cannot be null");
        }
        return repository.getAllProducts().stream()
                .filter(p -> p.getCreatedDate().isAfter(date))
                .collect(Collectors.toUnmodifiableList());
    }

    // getModifiedProducts
    public List<Product> getModifiedProducts() {
        return repository.getAllProducts().stream()
                .filter(p -> !p.getModifiedDate().equals(p.getCreatedDate()))
                .collect(Collectors.toUnmodifiableList());
    }

    // ---- Validation helpers ----
    private void validateProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("product cannot be null");
        }
        validateId(product.getId());
        validateName(product.getName());
        validateCategory(product.getCategory());
        validateRating(product.getRating());
    }

    private void validateId(String id) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("id cannot be null or blank");
        }
    }

    private void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("name cannot be null or blank");
        }
    }

    private void validateCategory(Category category) {
        if (category == null) {
            throw new IllegalArgumentException("category cannot be null");
        }
    }

    private void validateRating(int rating) {
        if (rating < 0 || rating > 10) {
            throw new IllegalArgumentException("rating must be 0..10");
        }
    }
}
