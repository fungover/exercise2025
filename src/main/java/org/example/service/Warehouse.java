package org.example.service;

import org.example.entities.Product;
import java.time.Clock;
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

     /*
    updateProduct(String id, String name, Category category, int rating) {

    }

    getProductsByCategorySorted(Category category) {

    }

    getProductsCreatedAfter(LocalDate date) {

    }

    getModifiedProducts() {

    }
    */
}
