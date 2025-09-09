package org.example.service;


import org.example.entities.Category;
import org.example.entities.Product;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class Warehouse {

    private final Map<String, Product> products = new HashMap<>();


    // addProduct(Product product): Add a new product (validate name is not empty)
    public void addProduct(Product product) {

            if (product == null) {
                throw new IllegalArgumentException("Product cannot be null");
            }
            if (product.name().isBlank()) {
                throw new IllegalArgumentException("Product name cannot be empty");
            }
            if (products.containsKey(product.id())) {
                throw new IllegalArgumentException("Product with this ID already exists");
            }
            products.put(product.id(), product);
        }

        // updateProduct(String id, String name, Category category, int rating): Modify an existing product
    public void updateProduct(String id, String name, Category category, int rating) {

        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Id cannot be null or empty");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        if (rating < 0 || rating > 10) {
            throw new IllegalArgumentException("Rating must be between 0 and 10");
        }

        Product existing = products.get(id);

        if (existing == null) {
            throw new IllegalArgumentException("Product with id " + id + " does not exist");
        }

        Product updated  = new Product(existing.id(), name, category, rating, existing.createdDate(), LocalDate.now());
        products.put(id, updated);
    }

    // getAllProducts(): Retrieve all products
    public List<Product> getAllProducts() {

        products.values().forEach(System.out::println); // Just to see the products in console, can be removed later
        return List.copyOf(products.values());
    }

    // getProductById(String id): Retrieve product by ID
    public Product getProductById(String id) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Id cannot be null or empty");
        }
        Product product = products.get(id);
        if (product == null) {
            throw new IllegalArgumentException("Product with id " + id + " does not exist");
        }
        return product;
    }

    /**
     * Get products by category sorted by name A-Z
     * @param category the category to filter by
     * @return list of products in the specified category sorted by name
     */
    public  List<Product> getProductsByCategorySorted(Category category) {
        return products.values()
                .stream()
                .filter(p -> p.category() == category)
                .sorted(Comparator.comparing(Product::name))
                .toList();
    }

    /**
     * Get products created after a specific date
     * @param date the date to filter by
     * @return list of products created after the specified date
     */
    public List<Product> getProductsCreatedAfter(LocalDate date) {
        return products.values()
                .stream()
                .filter(p -> p.createdDate().isAfter(date))
                .toList();
    }

    //getModifiedProducts(): Products where createdDate != modifiedDate
    public List<Product> getModifiedProducts() {
        return products.values()
                .stream()
                .filter(product -> !product.createdDate().equals(product.modifiedDate()))
                .toList();
    }

    /**
     * Get all categories that have at least one product
     *
     * @return set of categories with products
     */
    public Set<Category> getCategoriesWithProducts() {
        return products.values()
                .stream()
                .map(Product::category)
                .collect(Collectors.toSet());
    }

    /**
     * Count products in a specific category
     *
     * @param category the category to filter by
     * @return count of products in the specified category
     */
    public int countProductsInCategory(Category category){
        return (int) products.values()
                .stream()
                .filter(p -> p.category() == category)
                .count();
    }

    public Map<Character, Integer> getProductInitialsMap() {
        return products.values()
                .stream()
                .map(p -> p.name().toUpperCase().charAt(0))
                .collect(Collectors.toMap(
                        initial -> initial,
                        initial -> 1,
                        Integer::sum
                ));
    }

    /**
     * Get top 3 highest rated products created in the current month
     *
     * @return list of top 3 highest rated products created this month
     */
    public List<Product> getTopRatedProductsThisMonth() {
        LocalDate now = LocalDate.now();
        return products.values()
                .stream()
                .filter(p -> p.createdDate().getMonth() == now.getMonth() && p.createdDate().getYear() == now.getYear())
                .sorted(Comparator.comparingInt(Product::rating).reversed())
                .limit(3)
                .toList();
    }
}
