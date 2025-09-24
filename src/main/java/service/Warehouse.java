package service;

import entities.Category;
import entities.Product;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

//Jag ansvarar för att hantera min samling korrekt

public class Warehouse {

    private final Map<String, Product> products = new HashMap<>();

    public void addProduct(Product product) {
        // Validation
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }

        // Check that the product does not already exist
        if (product.id() == null || product.id().isBlank()) {
            throw new IllegalArgumentException("Product id cannot be null or blank");
        }
        if (products.containsKey(product.id())) {
            throw new IllegalArgumentException("Product with ID " + product.id() + " already exists");
        }

        // Add the product
        products.put(product.id(), product);
    }

    // Convenience method to add product using Builder pattern
    public void addNewProduct(String id, String name, Category category, int rating) {
        Product product = new Product.Builder()
                .id(id)
                .name(name)
                .category(category)
                .rating(rating)
                .asNewProduct()  // Sets current timestamps
                .build();

        addProduct(product);
    }

    // Updating an existing product - now uses Builder pattern
    public void updateProduct(String id, String name, Category category, int rating) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("id cannot be null or blank");
        }
        if (category == null) {
            throw new IllegalArgumentException("category cannot be null");
        }

        // Find existing product
        Product existingProduct = products.get(id);
        if (existingProduct == null) {
            throw new IllegalArgumentException("Product with ID " + id + " not found");
        }

        // Create updated product using Builder
        Product updatedProduct = new Product.Builder()
                .id(existingProduct.id())
                .name(name)
                .category(category)
                .rating(rating)
                .createdDate(existingProduct.createdDate())  // Keep original created date
                .modifiedDate(LocalDateTime.now())           // Update modified date
                .build();

        // Replace the old product
        products.put(id, updatedProduct);
    }

    // Fetching all products
    public List<Product> getAllProducts() {
        // Uses stream to create new list (avoid returning internal reference)
        return products.values()
                .stream()
                .collect(Collectors.toList());
    }

    // Gets a specific product by ID
    public Optional<Product> getProductById(String id) {
        // Optional helps us handle null values safely
        return Optional.ofNullable(products.get(id));
    }

    // Fetching products in a specific category, sorted A-Z by name
    public List<Product> getProductsByCategorySorted(Category category) {
        return products.values()
                .stream()
                .filter(product -> product.category().equals(category))  // Filter by category
                .sorted(Comparator.comparing(Product::name))             // Sort A-Z by name
                .collect(Collectors.toList());                           // Collect to list
    }

    // Fetching products created after a specific date
    public List<Product> getProductsCreatedAfter(LocalDate date) {
        return products.values()
                .stream()
                .filter(product -> product.createdDate().toLocalDate().isAfter(date))
                .collect(Collectors.toList());
    }

    // Fetching products that have been modified
    public List<Product> getModifiedProducts() {
        return products.values()
                .stream()
                .filter(Product::isModified)  // Use our isModified() method
                .collect(Collectors.toList());
    }

    // ========== VG-METHODS ==========

    // Fetching all categories that have at least one product
    public Set<Category> getCategoriesWithProducts() {
        return products.values()
                .stream()
                .map(Product::category)        // Stream<Category> - transform Product → Category
                .collect(Collectors.toSet());  // Set<Category> - collect and remove duplicates
    }

    // Return number of products in a category
    public long countProductsInCategory(Category category) {
        if (category == null) {
            throw new IllegalArgumentException("Category cannot be null");
        }

        return products.values().stream()
                .filter(product -> product.category() == category)
                .count();
    }

    // Return a Map of first letters in product names and their counts
    public Map<Character, Integer> getProductInitialsMap() {
        return products.values()
                .stream()
                .map(Product::name)
                .filter(name -> name != null && !name.isEmpty())
                .map(name -> name.toUpperCase().charAt(0))
                .collect(Collectors.groupingBy(
                        Function.identity(),
                        Collectors.counting()
                ))
                .entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().intValue()
                ));
    }

    // Products with max rating, created this month, sorted by newest first
    public List<Product> getTopRatedProductsThisMonth() {
        YearMonth currentMonth = YearMonth.now();

        List<Product> thisMonthProducts = products.values()
                .stream()
                .filter(product -> YearMonth.from(product.createdDate()).equals(currentMonth))
                .collect(Collectors.toList());

        if (thisMonthProducts.isEmpty()) {
            return new ArrayList<>();
        }

        int maxRating = thisMonthProducts.stream()
                .mapToInt(Product::rating)
                .max()
                .orElse(0);

        return thisMonthProducts.stream()
                .filter(product -> product.rating() == maxRating)
                .sorted(Comparator.comparing(Product::createdDate).reversed())
                .collect(Collectors.toList());
    }
}