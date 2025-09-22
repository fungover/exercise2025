package org.example.service;

import org.example.entities.Category;
import org.example.entities.Product;
import org.example.repository.ProductRepository;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;
import java.util.Map;
import java.util.OptionalInt;
import java.util.stream.Collectors;

public class Warehouse {
    private final ProductRepository productRepository;

    public Warehouse(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void addProduct(Product product) {
        productRepository.addProduct(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.getAllProducts();
    }

    public Optional<Product> getProductById(String id) {
        return productRepository.getProductById(id);
    }

    public void removeProduct(Product product) {
        productRepository.removeProduct(product);
    }

    public List<Product> getProductsByCategorySorted(Category category) {
        return productRepository.getAllProducts().stream()
                .filter(product -> product.category().equals(category))
                .sorted(Comparator.comparing(Product::name))
                .collect(Collectors.toList());
    }

    public List<Product> getProductsCreatedAfter(LocalDate cutoffDate) {
        return productRepository.getAllProducts().stream()
                .filter(product -> product.createdDate().isAfter(cutoffDate))
                .collect(Collectors.toList());
    }

    public List<Product> getModifiedProducts() {
        return productRepository.getAllProducts().stream()
                .filter(product -> !product.createdDate().equals(product.modifiedDate()))
                .collect(Collectors.toList());
    }

    public void updateProduct(String id, String name, Category category, int rating) {
        // Find existing product
        Optional<Product> existingProduct = productRepository.getProductById(id);

        if (existingProduct.isEmpty()) {
            throw new IllegalArgumentException("Product with id " + id + " not found");
        }

        // Create new product with updated values
        Product updatedProduct = Product.builder()
                .id(id)
                .name(name)
                .category(category)
                .rating(rating)
                .createdDate(existingProduct.get().createdDate())
                .modifiedDate(LocalDate.now())
                .build();

        productRepository.updateProduct(updatedProduct);
    }

    public int countProductsInCategory(Category category) {
        return (int) productRepository.getAllProducts().stream()
                .filter(product -> product.category().equals(category))
                .count();
    }

    public Set<Category> getCategoriesWithProducts() {
        return productRepository.getAllProducts().stream()
                // Extract category from every product
                .map(Product::category)
                // Collect to Set to remove duplicates, no need to use distinct() on Set
                .collect(Collectors.toSet());
    }

    public Map<Character, Integer> getProductInitialsMap() {
        return productRepository.getAllProducts().stream()
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
        List<Product> thisMonthProducts = productRepository.getAllProducts().stream()
                .filter(product -> YearMonth.from(product.createdDate()).equals(thisMonth))
                .collect(Collectors.toList());

        // Find max rating from these products
        OptionalInt maxRating = thisMonthProducts.stream()
                .mapToInt(Product::rating)
                .max();

        // If no products from this month return empty list
        if (maxRating.isEmpty()) {
            return Collections.emptyList();
        }

        // Filter on max rating and sort
        return thisMonthProducts.stream()
                .filter(product -> product.rating() == maxRating.getAsInt())
                .sorted(Comparator.comparing(Product::createdDate).reversed()
                        .thenComparing(Product::id))
                .collect(Collectors.toList());
    }
}
