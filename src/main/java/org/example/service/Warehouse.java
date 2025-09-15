package org.example.service;

import org.example.entities.Category;
import org.example.entities.Product;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


/**
 * Service class for managing warehouse products.
 * Provides methods to add, update, and retrieve products.
 */
public class Warehouse {

    private final List<Product> products = new ArrayList<>();

    /**
     * Adds a product to the warehouse after validation.
     *
     * @param product the product to add
     * @throws IllegalArgumentException if the product is invalid or duplicate
     */
    public void addProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        if (products.stream().anyMatch(p -> p.getId().equals(product.getId()))) {
            throw new IllegalArgumentException("Product with ID " + product.getId() + " already exists");
        }
        products.add(product);
    }

    /**
     * Returns all products in the warehouse.
     *
     * @return unmodifiable list of products
     */
    public List<Product> getAllProducts() {
        return Collections.unmodifiableList(products);
    }

    /**
     * Updates an existing product.
     *
     * @param id       the product ID
     * @param name     the new name
     * @param category the new category
     * @param rating   the new rating
     * @return the updated product
     * @throws IllegalArgumentException if input is invalid or product not found
     */
    public Product updateProduct(String id, String name, Category category, int rating) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Product ID cannot be null or blank");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Product name cannot be null or blank");
        }
        name = name.trim();
        if (category == null) {
            throw new IllegalArgumentException("Product category cannot be null");
        }
        if (rating < 0 || rating > 10) {
            throw new IllegalArgumentException("Product rating must be between 0 and 10");
        }

        Product existing = getProductById(id);

        Product updated = new Product.Builder()
                .id(id)
                .name(name)
                .category(category)
                .rating(rating)
                .createdDate(existing.getCreatedDate())
                .modifiedDate(LocalDate.now())
                .price(existing.getPrice())
                .build();

        int index = products.indexOf(existing);
        products.set(index, updated);

        return updated;
    }

    /**
     * Retrieves a product by ID.
     *
     * @param id the product ID
     * @return the product with the given ID
     * @throws IllegalArgumentException if id is invalid or not found
     */
    public Product getProductById(String id) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("id cannot be null/blank");
        }
        return products.stream()
                .filter(p -> Objects.equals(p.getId(), id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Product with ID " + id + " not found"));
    }

    /**
     * Retrieves products in a category, sorted by name.
     *
     * @param category the category to filter by
     * @return list of products in the category
     */
    public List<Product> getProductsByCategorySorted(Category category) {
        return products.stream()
                .filter(p -> p.getCategory()== category)
                .sorted((p1, p2) -> p1.getName().compareToIgnoreCase(p2.getName()))
                .toList();
    }

    /**
     * Retrieves products created after a given date.
     *
     * @param date the date threshold
     * @return list of products created after the date
     */
    public List<Product> getProductsCreatedAfter(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("date cannot be null");
        }
        return products.stream()
                .filter(p -> p.getCreatedDate().isAfter(date))
                .toList();
    }

    /**
     * Returns all products that have been modified.
     *
     * @return list of modified products
     */
    public List<Product> getModifiedProducts() {
        return products.stream()
                .filter(p -> !p.getCreatedDate().equals(p.getModifiedDate()))
                .toList();
    }

    /**
     * Returns all categories that have products.
     *
     * @return list of categories
     */
    public List<Category> getCategoriesWithProducts() {
        return products.stream()
                .map(Product::getCategory)
                .distinct()
                .toList();
    }

    /**
     * Counts products in a given category.
     *
     * @param category the category
     * @return number of products in the category
     */
    public long countProductsInCategory(Category category) {
        return products.stream()
                .filter(p -> p.getCategory()== category)
                .count();
    }

    /**
     * Builds a map of product name initials and their counts.
     *
     * @return map of initials to counts
     */
    public Map<Character, Integer> getProductInitialsMap() {
        return products.stream()
                .map(p -> Character.toUpperCase(p.getName().charAt(0)))
                .collect(Collectors.groupingBy(
                        c -> c,
                        Collectors.counting()
                ))
                .entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> e.getValue().intValue()
                ));
    }

    /**
     * Retrieves top-rated products created this month.
     *
     * @return list of top-rated products, newest first
     */
    public List<Product> getTopRatedProductsThisMonth() {
        LocalDate now = LocalDate.now();
        List<Product> thisMonth = products.stream()
                .filter(p -> p.getCreatedDate().getMonth() == now.getMonth()
                        && p.getCreatedDate().getYear() == now.getYear())
                .toList();

        OptionalInt maxRating = thisMonth.stream()
                .mapToInt(Product::getRating)
                .max();

        if (maxRating.isEmpty()) return Collections.emptyList();

        return thisMonth.stream()
                .filter(p -> p.getRating() == maxRating.getAsInt())
                .sorted((p1, p2) -> {
                    int cmp = p2.getCreatedDate().compareTo(p1.getCreatedDate());
                    if (cmp != 0) return cmp;
                    return p1.getName().compareToIgnoreCase(p2.getName());
                })
                .toList();
    }
}