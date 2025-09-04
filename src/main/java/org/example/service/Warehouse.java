package org.example.service;

import org.example.entities.Category;
import org.example.entities.Product;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class Warehouse {

    private final Map<String, Product> products = new HashMap<>(); // Using Hashmap to sore products. Key = ID, Value = Product.

    //Method to add a new product.
    public void addProduct(Product product) {

        if (product.name().trim().isEmpty()) { // Check if the product name is empty or just whitespace
            throw new IllegalArgumentException("Product name cannot be empty");
        }

        products.put(product.id(), product); // Add the product to the map using its ID as the key
    }

    public void updateProduct(String id, String name, Category category, int rating) {

        Product existingProduct = products.get(id);

        if (existingProduct == null) {
            throw new IllegalArgumentException("Product with id " + id + " not found");
        }

        if (name.trim().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be empty");
        }

        //Since records are immutable, we need to create a new instance of the Product record with the updated values.
        Product updatedProduct = new Product(
                id,
                name,
                category,
                rating,
                existingProduct.createdDate(),
                LocalDate.now());

        products.put(id, updatedProduct); // Update the product in the map
    }

    public List<Product> getAllProducts() {
        return new ArrayList<>(products.values()); //Return a copy of the list of products. This way the original list cannot be modified from outside.
    }

    public Optional<Product> getProductById(String id) { //Using Optional to handle the case where the product might not be found.
        return Optional.ofNullable(products.get(id)); // Return an Optional containing the product if found, or an empty Optional if not found.
    }

    /*
    WORKING ON STREAMS HERE
     */

    // Get products by category and sort them by name (A-Z).

    public List<Product> getProductsByCategorySorted(Category category) {
        return products.values().stream() // Convert the collection of products to a stream
                .filter(product -> product.category() == category) // Filter products by the specified category
                .sorted(Comparator.comparing(Product::name)) // Sort the filtered products by name in ascending order
                .collect(Collectors.toList()); // Collect the sorted products into a list and return it
    }

    // Get products created after a given date.

    public List<Product> getProductsCreatedAfter(LocalDate date) {
        return products.values().stream()
                .filter(product -> product.createdDate().isAfter(date)) // Filter products created after the specified date
                .collect(Collectors.toList()); // Collect the filtered products into a list and return it
    }

    // Get products where createdDate != modifiedDate
    public List<Product> getModifiedProducts() {
        return products.values().stream() // Convert the collection of products to a stream
                .filter(product -> !product.createdDate().equals(product.modifiedDate())) // Filter products where the createdDate is not equal to the modifiedDate
                .collect(Collectors.toList()); // Collect the filtered products into a list and return it
    }

    // Return all categories that have at least one product in the warehouse.
    public Set<Category> getCategoriesWithProducts() {
        return products.values().stream()
                .map(Product::category) // Extract the category from each product
                .collect(Collectors.toSet()); // Collect the categories into a set to ensure uniqueness.
    }

    // Return the number of products in a specific category.
    public long countProductsInCategory(Category category) {
        return products.values().stream()
                .filter(product -> product.category() == category) // Filter products by the specified category
                .count(); // Count the number of products in the filtered stream
    }


    /*
    HELPER METHODS FOR CREATING PRODUCTS WITH DIFFERENT DATES FOR TESTING PURPOSES
     */
    public static Product createProduct(String id, String name, Category category, int rating) {
        LocalDate now = LocalDate.now();
        return new Product(id, name, category, rating, now, now);
    }

    //Helper method to create a product with old dates for testing purposes
    public static Product createOldProduct(String id, String name, Category category, int rating, int daysAgo) {
        LocalDate oldDate = LocalDate.now().minusDays(daysAgo);
        return new Product(id, name, category, rating, oldDate, oldDate);
    }

    //Helper method to create a product with specific created and modified dates for testing purposes
    public static Product createProductWithDate(String id, String name, Category category, int rating, LocalDate createdDate) {
        return new Product(id, name, category, rating, createdDate, createdDate);
    }


}
