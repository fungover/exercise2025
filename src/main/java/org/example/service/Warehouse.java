package org.example.service;

import org.example.entities.Category;
import org.example.entities.Product;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public final class Warehouse {
    private final Map<String, Product> store = new HashMap<>();
    private final Clock clock;

    public Warehouse() {
        this(Clock.systemDefaultZone());
    }

    public Warehouse(Clock clock) {
        this.clock = Objects.requireNonNull(clock, "clock");
    }

    public List<Product> getAllProducts() {
        return List.copyOf(store.values());
    }

    public void addProduct(Product product) {
        Objects.requireNonNull(product, "product");
        if (store.containsKey(product.id())) {
            throw new IllegalArgumentException("duplicate id: " + product.id());
        }
        store.put(product.id(), product);
    }

    public Optional<Product> getProductById(String id) {
        Objects.requireNonNull(id, "id");
        if (id.isBlank()) throw new IllegalArgumentException("id required");
        return Optional.ofNullable(store.get(id));
    }

    public Product updateProduct(String id, String name, Category category, int rating) {
        Objects.requireNonNull(id, "id");
        if (id.isBlank()) throw new IllegalArgumentException("id required");

        Product existing = Optional.ofNullable(store.get(id))
                .orElseThrow(() -> new NoSuchElementException("not found: " + id));

        LocalDateTime now = LocalDateTime.now(clock);
        Product updated = existing.withUpdated(name, category, rating, now);

        store.put(id, updated);
        return updated;
    }

    public List<Product> getProductsByCategorySorted(Category category) {
        Objects.requireNonNull(category, "category");
        return store.values().stream()
                .filter(product -> product.category() == category)
                .sorted(Comparator.comparing(Product::name, String.CASE_INSENSITIVE_ORDER))
                .toList();
    }

    public List<Product> getProductsCreatedAfter(LocalDate date) {
        Objects.requireNonNull(date, "date");
        return store.values().stream()
                .filter(product -> product.createdDate().toLocalDate().isAfter(date))
                .toList();
    }

    public List<Product> getModifiedProducts() {
        return store.values().stream()
                .filter(p -> !p.createdDate().equals(p.modifiedDate()))
                .toList();
    }

    public List<Category> getCategoriesWithProducts() {
        return store.values().stream()
                .map(Product::category)
                .distinct()
                .toList();
    }

    public long countProductsInCategory(Category category) {
        Objects.requireNonNull(category, "category");
        return store.values().stream()
                .filter(product -> product.category() == category)
                .count();
    }

    /*
    getProductInitialsMap(): Return a Map<Character, Integer> of first letters in product names and their counts

    getTopRatedProductsThisMonth(): Products with max rating, created this month, sorted by newest first

 */
}
