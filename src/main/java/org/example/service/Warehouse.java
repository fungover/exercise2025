package org.example.service;

import org.example.entities.Category;
import org.example.entities.Product;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class Warehouse {
    private final Map<String, Product> products;

    public Warehouse() {
        this.products = new ConcurrentHashMap<>();
    }

    public void addProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        if (product.id() == null || product.id().trim().isEmpty()) {
            throw new IllegalArgumentException("Product ID cannot be empty");
        }
        if (product.name() == null || product.name().trim().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be empty");
        }
        if (products.containsKey(product.id())) {
            throw new IllegalArgumentException("Product with ID " + product.id() + " already exists");
        }
        products.put(product.id(), product);
    }

    public void updateProduct(String id, String name, Category category, int rating) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Product ID cannot be empty");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be empty");
        }

        Product existingProduct = products.get(id);
        if (existingProduct == null) {
            throw new IllegalArgumentException("Product with ID " + id + " not found");
        }

        Product updatedProduct = existingProduct.withUpdatedFields(name, category, rating);
        products.put(id, updatedProduct);
    }

    public List<Product> getAllProducts() {
        return new ArrayList<>(products.values());
    }

    public Optional<Product> getProductById(String id) {
        if (id == null || id.trim().isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(products.get(id));
    }

    public List<Product> getProductsByCategorySorted(Category category) {
        if (category == null) {
            return new ArrayList<>();
        }

        return products.values().stream()
                .filter(product -> product.category() == category)
                .sorted(Comparator.comparing(Product::name))
                .collect(Collectors.toList());
    }

    public List<Product> getProductsCreatedAfter(LocalDate date) {
        if (date == null) {
            return new ArrayList<>();
        }

        return products.values().stream()
                .filter(product -> product.createdDate().isAfter(date))
                .collect(Collectors.toList());
    }

    public List<Product> getModifiedProducts() {
        return products.values().stream()
                .filter(product -> !product.createdDate().equals(product.modifiedDate()))
                .collect(Collectors.toList());
    }

    //------------- VG Distinction Methods --------------
     // Return all categories that have at least one product
    public List<Category> getCategoriesWithProducts() {
        return products.values().stream()
                .map(Product::category)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }


    // Return the number of products in a specific category
    public long countProductsInCategory(Category category) {
        if (category == null) {
            return 0;
        }
        return products.values().stream()
                .filter(product -> product.category() == category)
                .count();
    }


     // Return a map of first letters in product names and their counts
    public Map<Character, Integer> getProductInitialsMap() {
        return products.values().stream()
                .filter(product -> !product.name().isEmpty())
                .collect(Collectors.groupingBy(
                    product -> Character.toUpperCase(product.name().charAt(0)),
                    Collectors.collectingAndThen(Collectors.counting(), Math::toIntExact)
                ));
    }


     // Return products with max rating, created this month, sorted by newest first
    public List<Product> getTopRatedProductsThisMonth() {
        LocalDate startOfMonth = LocalDate.now().withDayOfMonth(1);
        LocalDate endOfMonth = LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth());

        // Find the maximum rating among products created this month
        OptionalInt maxRating = products.values().stream()
                .filter(product -> !product.createdDate().isBefore(startOfMonth) &&
                                 !product.createdDate().isAfter(endOfMonth))
                .mapToInt(Product::rating)
                .max();

        if (maxRating.isEmpty()) {
            return new ArrayList<>();
        }

        return products.values().stream()
                .filter(product -> !product.createdDate().isBefore(startOfMonth) &&
                                 !product.createdDate().isAfter(endOfMonth))
                .filter(product -> product.rating() == maxRating.getAsInt())
                .sorted(Comparator.comparing(Product::createdDate).reversed())
                .collect(Collectors.toList());
    }
}