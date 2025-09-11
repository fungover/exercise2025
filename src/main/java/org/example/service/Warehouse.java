package org.example.service;

import org.example.entities.Category;
import org.example.entities.Product;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;
import java.util.Map;
import java.util.OptionalInt;
import java.util.stream.Collectors;

public class Warehouse {

    private final List<Product> products = new ArrayList<>();

    public void addProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        if (product.name() == null || product.name().trim().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be empty");
        }
        if (product.category() == null) {
            throw new IllegalArgumentException("Product category cannot be null");
        }
        products.add(product);
    }

    public List<Product> getAllProducts() {
        return Collections.unmodifiableList(new ArrayList<>(products));
    }

    public Optional<Product> getProductById(String id) {
        return products.stream()
                .filter(product -> product.id().equals(id))
                .findFirst();
    }

    public List<Product> getProductsByCategorySorted(Category category) {
        return products.stream()
                .filter(product -> product.category().equals(category))
                .sorted(Comparator.comparing(Product::name))
                .collect(Collectors.toList());
    }

    public List<Product> getProductsCreatedAfter(LocalDate cutoffDate) {
        return products.stream()
                .filter(product -> product.createdDate().isAfter(cutoffDate))
                .collect(Collectors.toList());
    }

    public List<Product> getModifiedProducts() {
        return products.stream()
                .filter(product -> !product.createdDate().equals(product.modifiedDate()))
                .collect(Collectors.toList());
    }

    public void updateProduct(String id, String name, Category category, int rating) {
        // Find existing product
        Optional<Product> existingProduct = products.stream()
                .filter(product -> product.id().equals(id))
                .findFirst();

        if (existingProduct.isEmpty()) {
            throw new IllegalArgumentException("Product with id " + id + " not found");
        }

        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be empty");
        }
        if (category == null) {
            throw new IllegalArgumentException("Product category cannot be null");
        }
        // Remove old product
        products.removeIf(product -> product.id().equals(id));

        // Create new product with updated values
        Product updatedProduct = new Product(
                id,
                name,
                category,
                rating,
                existingProduct.get().createdDate(),
                LocalDate.now()
        );

        products.add(updatedProduct);
    }

    public int countProductsInCategory(Category category) {
        return (int) products.stream()
                .filter(product -> product.category().equals(category))
                .count();
    }

    public Set<Category> getCategoriesWithProducts() {
        return products.stream()
                // Extract category from every product
                .map(Product::category)
                // Collect to Set to remove duplicates, no need to use distinct() on Set
                .collect(Collectors.toSet());
    }

    public Map<Character, Integer> getProductInitialsMap() {
        return products.stream()
                // Group products by first letter in name
                .collect(Collectors.groupingBy(
                        // Key: First letter
                        product -> product.name().charAt(0),
                        // Wrapper to transform result
                        Collectors.collectingAndThen(
                                // Count (gives Long)
                                Collectors.counting(),
                                // Convert Long to Integer
                                Math::toIntExact
                        )
                ));
    }

    public List<Product> getTopRatedProductsThisMonth() {
        LocalDate now = LocalDate.now();
        YearMonth thisMonth = YearMonth.from(now);

        // Find products from this month
        List<Product> thisMonthProducts = products.stream()
                .filter(product -> YearMonth.from(product.createdDate()).equals(thisMonth))
                .toList();

        // Find max rating from these products
        OptionalInt maxRating = thisMonthProducts.stream()
                .mapToInt(Product::rating)
                .max();

        // If no products from this month return empty list
        if (maxRating.isEmpty()) {
            return new ArrayList<>();
        }

        // Filter on max rating and sort
        return thisMonthProducts.stream()
                .filter(product -> product.rating() == maxRating.getAsInt())
                .sorted((p1, p2) -> p2.createdDate().compareTo(p1.createdDate()))
                .collect(Collectors.toList());
    }
}
