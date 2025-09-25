package org.example.service;

import org.example.entities.Category;
import org.example.entities.Product;
import org.example.repository.ProductRepository;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class ProductService {
    private final Clock clock;
    private final ProductRepository productRepository;

    public ProductService(Clock clock, ProductRepository productRepository) {
        this.clock = Objects.requireNonNull(clock, "clock");
        this.productRepository = Objects.requireNonNull(productRepository, "productRepository");
    }

    public List<Product> getAllProducts() {
        return productRepository.getAllProducts();
    }

    public void addProduct(Product product) {
        productRepository.addProduct(product);
    }

    public Optional<Product> getProductById(String id) {
        return productRepository.getProductById(id);
    }

    public Product updateProduct(String id, String name, Category category, int rating, double price) {
        Objects.requireNonNull(id, "id");
        if (id.isBlank()) throw new IllegalArgumentException("id required");

        Product existing = productRepository.getProductById(id)
                .orElseThrow(() -> new NoSuchElementException("not found: " + id));

        LocalDateTime now = LocalDateTime.now(clock);
        Product updated = existing.withUpdated(name, category, rating, now, price);
        productRepository.updateProduct(updated);
        return updated;
    }

    public List<Product> getProductsByCategorySorted(Category category) {
        Objects.requireNonNull(category, "category");
        return getAllProducts().stream()
                .filter(p -> p.category() == category)
                .sorted(Comparator.comparing(Product::name, String.CASE_INSENSITIVE_ORDER))
                .toList();
    }

    public List<Product> getProductsCreatedAfter(LocalDate date) {
        Objects.requireNonNull(date, "date");
        return getAllProducts().stream()
                .filter(p -> p.createdDate().toLocalDate().isAfter(date))
                .toList();
    }

    public List<Product> getModifiedProducts() {
        return getAllProducts().stream()
                .filter(p -> !p.createdDate().equals(p.modifiedDate()))
                .toList();
    }

    public List<Category> getCategoriesWithProducts() {
        return getAllProducts().stream()
                .map(Product::category)
                .distinct()
                .toList();
    }

    public long countProductsInCategory(Category category) {
        Objects.requireNonNull(category, "category");
        return getAllProducts().stream()
                .filter(p -> p.category() == category)
                .count();
    }

    public Map<Character, Integer> getProductInitialsMap() {
        Map<Character, Integer> counts = getAllProducts().stream()
                .map(p -> Character.toUpperCase(p.name().charAt(0)))
                .collect(Collectors.groupingBy(
                        Function.identity(),
                        Collectors.collectingAndThen(Collectors.counting(), Long::intValue)
                ));
        return Map.copyOf(counts);
    }

    public List<Product> getTopRatedProductsThisMonth() {
        YearMonth currentMonth = YearMonth.now(clock);

        List<Product> inMonth = getAllProducts().stream()
                .filter(p -> YearMonth.from(p.createdDate()).equals(currentMonth))
                .toList();

        if (inMonth.isEmpty()) return List.of();

        int top = inMonth.stream().mapToInt(Product::rating).max().orElseThrow();

        return inMonth.stream()
                .filter(p -> p.rating() == top)
                .sorted(Comparator.comparing(Product::createdDate).reversed())
                .toList();
    }
}
