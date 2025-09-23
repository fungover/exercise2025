package org.example.service;

import org.example.Repository.ProductRepository;
import org.example.entities.Category;
import org.example.entities.Product;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


/**
 * Service class for managing warehouse products.
 * Delegates data access to ProductRepository.
 * Provides methods for CRUD operations and various queries.
 */
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = Objects.requireNonNull(productRepository, "Repository cannot be null");
    }

    /**
     * Adds a product to the repository.
     *
     */
    public void addProduct(Product product) {
        if (product == null) throw new IllegalArgumentException("Product cannot be null");
        productRepository.addProduct(product);
    }

    /**
     * Gets a product by ID.
     *
     */
    public Product getProductById(String id) {
        if (id == null || id.isBlank()) throw new IllegalArgumentException("id cannot be null/blank");
        return productRepository.getProduct(id)
                .orElseThrow(() -> new IllegalArgumentException("Product with ID " + id + " not found"));
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

        productRepository.updateProduct(updated);
        return updated;
    }

    /**
     * Gets all products.
     *
     */
    public List<Product> getAllProducts() {
        return Collections.unmodifiableList(productRepository.getAllProducts());
    }

    /**
     * Gets product in a category.
     *
     */
    public List<Product> getProductsByCategorySorted(Category category) {
        return productRepository.getAllProducts().stream()
                .filter(p -> p.getCategory() == category)
                .sorted(Comparator.comparing(Product::getName, String.CASE_INSENSITIVE_ORDER))
                .toList();
    }

    /**
     * Gets products created after a given date.
     *
     * @param date the date threshold
     * @return list of products created after the date
     */
    public List<Product> getProductsCreatedAfter(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("date cannot be null");
        }
        return productRepository.getAllProducts().stream()
                .filter(p -> p.getCreatedDate().isAfter(date))
                .toList();
    }

    /**
     * Returns all products that have been modified.
     *
     * @return list of modified products
     */
    public List<Product> getModifiedProducts() {
        return productRepository.getAllProducts().stream()
                .filter(p -> !p.getCreatedDate().equals(p.getModifiedDate()))
                .toList();
    }

    /**
     * Returns all categories that have products.
     *
     * @return list of categories
     */
    public List<Category> getCategoriesWithProducts() {
        return productRepository.getAllProducts().stream()
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
        return productRepository.getAllProducts().stream()
                .filter(p -> p.getCategory()== category)
                .count();
    }

    /**
     * Builds a map of product name initials and their counts.
     *
     * @return map of initials to counts
     */
    public Map<Character, Integer> getProductInitialsMap() {
        return productRepository.getAllProducts().stream()
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
        List<Product> thisMonth = productRepository.getAllProducts().stream()
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