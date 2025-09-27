package org.example.service;

import org.example.entities.Category;
import org.example.entities.Product;
import org.example.repository.ProductRepository;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void addProduct(Product product) {
        productRepository.addProduct(product);
    }

    public void updateProduct(String id, String name, Category category, int rating) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Product ID cannot be empty");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be empty");
        }
        if (category == null) {
            throw new IllegalArgumentException("Category cannot be null");
        }
        if (rating < 0 || rating > 10) {
            throw new IllegalArgumentException("Rating must be between 0 and 10");
        }

        Optional<Product> existingProduct = productRepository.getProductById(id);
        if (existingProduct.isEmpty()) {
            throw new IllegalArgumentException("Product with ID " + id + " not found");
        }

        Product updatedProduct = existingProduct.get().withUpdatedFields(name, category, rating);
        productRepository.updateProduct(updatedProduct);
    }

    public List<Product> getAllProducts() {
        return productRepository.getAllProducts();
    }

    public Optional<Product> getProductById(String id) {
        return productRepository.getProductById(id);
    }

    public List<Product> getProductsByCategorySorted(Category category) {
        if (category == null) {
            return new ArrayList<>();
        }

        return productRepository.getAllProducts().stream()
                .filter(product -> product.category() == category)
                .sorted(Comparator.comparing(Product::name))
                .collect(Collectors.toList());
    }

    public List<Product> getProductsCreatedAfter(LocalDate date) {
        if (date == null) {
            return new ArrayList<>();
        }

        return productRepository.getAllProducts().stream()
                .filter(product -> product.createdDate().isAfter(date))
                .collect(Collectors.toList());
    }

    public List<Product> getModifiedProducts() {
        return productRepository.getAllProducts().stream()
                .filter(product -> !product.createdDate().equals(product.modifiedDate()))
                .collect(Collectors.toList());
    }

    public List<Category> getCategoriesWithProducts() {
        return productRepository.getAllProducts().stream()
                .map(Product::category)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }

    public long countProductsInCategory(Category category) {
        if (category == null) {
            return 0;
        }
        return productRepository.getAllProducts().stream()
                .filter(product -> product.category() == category)
                .count();
    }

    public Map<Character, Integer> getProductInitialsMap() {
        return productRepository.getAllProducts().stream()
                .filter(product -> !product.name().isEmpty())
                .collect(Collectors.groupingBy(
                    product -> Character.toUpperCase(product.name().charAt(0)),
                    Collectors.collectingAndThen(Collectors.counting(), Math::toIntExact)
                ));
    }

    public List<Product> getTopRatedProductsThisMonth() {
        LocalDate today = LocalDate.now();
        LocalDate startOfMonth = today.withDayOfMonth(1);
        LocalDate endOfMonth = today.withDayOfMonth(today.lengthOfMonth());

        OptionalInt maxRating = productRepository.getAllProducts().stream()
                .filter(product -> !product.createdDate().isBefore(startOfMonth) &&
                                 !product.createdDate().isAfter(endOfMonth))
                .mapToInt(Product::rating)
                .max();

        if (maxRating.isEmpty()) {
            return new ArrayList<>();
        }

        return productRepository.getAllProducts().stream()
                .filter(product -> !product.createdDate().isBefore(startOfMonth) &&
                                 !product.createdDate().isAfter(endOfMonth))
                .filter(product -> product.rating() == maxRating.getAsInt())
                .sorted(Comparator.comparing(Product::createdDate).reversed())
                .collect(Collectors.toList());
    }
}
