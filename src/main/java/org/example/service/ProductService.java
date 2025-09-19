package org.example.service;

import org.example.entities.Category;
import org.example.entities.Product;
import org.example.repository.ProductRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

import java.util.Comparator;
import java.util.Objects;
import java.util.stream.Collectors;


public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

        public void addProduct (Product product){
            if (product == null) {
                throw new IllegalArgumentException("Product cannot be null");
            }
            if (product.id() == null || product.id().isBlank()) {
                throw new IllegalArgumentException("Product id cannot be empty");

            }
            if (product.name() == null || product.name().isBlank()) {
                throw new IllegalArgumentException("Product name cannot be empty");
            }
            if (product.category() == null) {
                throw new IllegalArgumentException("Category cannot be null");

            }
            if (product.rating() < 0 || product.rating() > 10) {
                throw new IllegalArgumentException("Product rating must be between 0 and 10");
            }
            if (product.createdDate() == null || product.modifiedDate() == null) {
                throw new IllegalArgumentException("Product dates cannot be null");
            }
            if (productRepository.getAllProducts().stream()
                    .anyMatch(p -> Objects.equals(p.id(), product.id()))) {
                throw new IllegalArgumentException("Product id already exists");
            }
            productRepository.addProduct(product);
        }


        public List<Product> getAllProducts () {
            return productRepository.getAllProducts();
        }

        public void updateProduct (String id, String name, Category category, int rating) {
            if (id == null || id.isBlank()) {
                throw new IllegalArgumentException("Product id cannot be empty");
            }
            if (name == null || name.isBlank()) {
                throw new IllegalArgumentException("Product name cannot be empty");
            }
            if (category == null) {
                throw new IllegalArgumentException("Category cannot be null");
            }
            if (rating < 0 || rating > 10) {
                throw new IllegalArgumentException("Product rating must be between 0 and 10");
            }
            Product existing = productRepository.getProductById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Product not found"));

                    Product updated = new Product.Builder()
                            .id(existing.id())
                            .name(name)
                            .category(category)
                            .rating(rating)
                            .createdDate(existing.createdDate())
                            .modifiedDate(LocalDate.now())
                            .build();

                    productRepository.updateProduct(updated);

        }
        public Product getProductById (String id){
            if (id == null || id.isBlank()) {
                throw new IllegalArgumentException("Product id cannot be empty");
            }
            return productRepository.getAllProducts().stream()
                    .filter(p -> Objects.equals(p.id(), id))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        }

        public List<Product> getProductsByCategorySorted (Category category){
            if (category == null) {
                throw new IllegalArgumentException("Category cannot be null");
            }
            return productRepository.getAllProducts().stream()
                    .filter(p -> p.category() == category)
                    .sorted(Comparator.comparing(Product::name, String.CASE_INSENSITIVE_ORDER))
                    .collect(Collectors.toList());
        }
        public List<Product> getProductsCreatedAfter (LocalDate date){
            if (date == null) {
                throw new IllegalArgumentException("Date cannot be null");
            }
            return productRepository.getAllProducts().stream()
                    .filter(p -> p.createdDate().isAfter(date))
                    .collect(Collectors.toList());
        }
        public List<Product> getModifiedProducts () {
            return productRepository.getAllProducts().stream()
                    .filter(p -> !p.createdDate().equals(p.modifiedDate()))
                    .collect(Collectors.toList());
        }
        // --- VG methods ---
        public java.util.Set<Category> getCategoriesWithProducts () {
            return productRepository.getAllProducts().stream()
                    .map(Product::category)
                    .collect(java.util.stream.Collectors.toSet());
        }
        public long countProductsInCategory (Category category){
            if (category == null) {
                throw new IllegalArgumentException("Category cannot be null");
            }
            return productRepository.getAllProducts().stream()
                    .filter(p -> p.category() == category)
                    .count();
        }
        public java.util.Map<Character, Integer> getProductInitialsMap () {
            return productRepository.getAllProducts().stream()
                    .map(Product::name)
                    .filter(n -> n != null && !n.isBlank())
                    .map(n -> Character.toUpperCase(n.charAt(0)))
                    .collect(java.util.stream.Collectors.toMap(
                            c -> c,
                            c -> 1,
                            Integer::sum
                    ));
        }
        public List<Product> getTopRatedProductsThisMonth () {
            LocalDate now = LocalDate.now();
            int year = now.getYear();
            int month = now.getMonthValue();

            var thisMonth = productRepository.getAllProducts().stream()
                    .filter(p -> p.createdDate().getYear() == year && p.createdDate().getMonthValue() == month)
                    .collect(Collectors.toList());

            if (thisMonth.isEmpty()) {
                return List.of();
            }

            int maxRating = thisMonth.stream()
                    .mapToInt(Product::rating)
                    .max()
                    .orElse(0);

            return thisMonth.stream()
                    .filter(p -> p.rating() == maxRating)
                    .sorted(Comparator.comparing(Product::createdDate).reversed())
                    .collect(Collectors.toList());
        }

    }




