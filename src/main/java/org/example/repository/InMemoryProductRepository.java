package org.example.repository;

import org.example.entities.Product;

import java.time.LocalDate;
import java.util.*;


public class InMemoryProductRepository implements ProductRepository {


    private final Map<String, Product> products = new HashMap<>();
    // Move the List or Map that stores your products from the Warehouse into this new class.

    @Override
    public void addProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        if (product.getId() == null || product.getId().isBlank()) {
            throw new IllegalArgumentException("Product id cannot be null or empty");
        }
        if (product.getName().isBlank()) {
            throw new IllegalArgumentException("Product name cannot be empty");
        }
        if (product.category() == null) {
            throw new IllegalArgumentException("Category cannot be null");
        }
        if (products.containsKey(product.getId())) {
            throw new IllegalArgumentException("Product with this ID already exists");
        }
        products.put(product.getId(), product);
    }

    @Override
    public Optional<Product> getProductByID(String id) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Id cannot be null or empty");
        }
        Product product = products.get(id);
        if (product == null) {
            throw new IllegalArgumentException("Product with id " + id + " does not exist");
        }
        return Optional.of(product);
    }

    @Override
    public List<Product> getAllProducts() {
        /*products.values().forEach(System.out::println);*/ // Just to see the products in console, can be removed later
        return Collections.unmodifiableList(new ArrayList<>(products.values()));

    }

    @Override
    public void updateProduct(Product product) {
        if (product.getId() == null || product.getId().isBlank()) {
            throw new IllegalArgumentException("Id cannot be null or empty");
        }
        if (product.getName() == null || product.getName().isBlank()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        if (product.rating() < 0 || product.rating() > 10) {
            throw new IllegalArgumentException("Rating must be between 0 and 10");
        }

        Product existing = products.get(product.getId());

        if (existing == null) {
            throw new IllegalArgumentException("Product with id " + product.getId() + " does not exist");
        }

        Product updated = new Product.Builder()
                .id(existing.getId())
                .name(product.getName())
                .category(product.category())
                .rating(product.rating())
                .createdDate(existing.createdDate())
                .modifiedDate(LocalDate.now())
                .price(product.getPrice())
                .build();

        products.put(product.getId(), updated);
    }
}
