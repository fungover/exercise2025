package org.example.service;

import org.example.entities.Category;
import org.example.entities.Product;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


public class Warehouse {

    private final Map<String, Product> products = new HashMap<>(); // Using Hashmap to store products. Key = ID, Value = Product.

    //Method to add a new product.
    public void addProduct(Product product) {
        Objects.requireNonNull(product, "Product cannot be null");
        products.put(product.id(), product); // Add the product to the map using its ID as the key
    }

    public void updateProduct(String id, String name, Category category, int rating) {

        Product existingProduct = products.get(id);

        if (existingProduct == null) {
            throw new IllegalArgumentException("Product with id " + id + " not found");
        }


        //Since records are immutable, we need to create a new instance of the Product record with the updated values.
        Product updatedProduct = new Product.Builder()
                .id(id)
                .name(name)
                .category(category)
                .rating(rating)
                .createdDate(existingProduct.createdDate()) // Keep the original created date
                .modifiedDate(LocalDate.now()) // Update the modified date to the current date
                .build();

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
                .sorted(Comparator.comparing(Product::name, String.CASE_INSENSITIVE_ORDER)) // Sort the filtered products by name in ascending order
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

    // Return a Map<Character, Integer> of the first letters in product names and their counts.
    public Map<Character, Integer> getProductInitialsMap() {
        return products.values().stream() // Taking all the product names with values.
                .map(product -> Character.toUpperCase(product.name().trim().charAt(0)))  // Mapping to the first character of each product name and converting it to uppercase.
                .collect(Collectors.groupingBy(  // Grouping by the first character.
                        character -> character,
                        Collectors.collectingAndThen(
                                Collectors.counting(),  // Collecting the number of counts for each character.
                                Math::toIntExact  // Converting Long to Integer
                        )
                ));
    }

    // Return products with max rating, created this month, sorted by newst first.
    public List<Product> getTopRatedProductsThisMonth() {

        LocalDate startOfMonth = LocalDate.now().withDayOfMonth(1); // Get the first day of the current month

        OptionalInt maxRating = products.values().stream()
                .filter(product -> !product.createdDate().isBefore(startOfMonth)) // Filter products created this month
                .mapToInt(Product::rating) // Map to their ratings
                .max(); // Find the maximum rating among the filtered products

        if (maxRating.isEmpty()) { // If no products were created this month, return an empty list
            return Collections.emptyList();
        }
        // Return products with max rating, created this month, sorted by the newest first.
        return products.values().stream()
                .filter(product -> !product.createdDate().isBefore(startOfMonth)) // Filter products created this month
                .filter(product -> product.rating() == maxRating.getAsInt()) // Filter products with the maximum rating
                .sorted(Comparator.comparing(Product::createdDate).reversed()) // Sort by created date in descending order (newest first)
                .collect(Collectors.toList());
    }
}
