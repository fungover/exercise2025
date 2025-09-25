package service;

import entities.Category;
import entities.Product;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;


public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void addProduct(Product product) {
        if (product.name().isEmpty()) {
            throw new IllegalArgumentException("Name can not be empty");
        }
        productRepository.addProduct(product);

    }

    public Optional<Product> getProductById(String id) {
        return productRepository.getProductById(id);
    }

    public List<Product> getAllProducts() {
        return productRepository.getAllProducts();
    }

    public void updateProduct(Product product) {
        productRepository.updateProduct(product);
    }


    public void updateProduct(String id, String name, Category category, int rating) {
        Product existing = getProductById(String.valueOf(id))
                .orElseThrow(() -> new NoSuchElementException("Product with id " + id + " not found"));

        Product updated = new Product.Builder()
                .id(id)
                .name(name)
                .category(category)
                .rating(rating)
                .createdAt(existing.createdAt())
                .modifiedAt(LocalDateTime.now())
                .build();

        productRepository.updateProduct(updated);
    }

    public List<Product> getProductsByCategorySorted(Category category) {
        return getAllProducts().stream()
                .filter(p -> p.category() == category)
                .sorted(Comparator.comparing(Product::name))
                .toList();

    }

    public List<Product> getProductsCreatedAfter(LocalDate date) {
        return getAllProducts().stream()
                .filter(p -> p.createdAt().toLocalDate().isAfter(date))
                .toList();
    }

    public List<Product> getModifiedProducts() {
        return getAllProducts().stream()
                .filter(p -> !p.createdAt().equals(p.modifiedAt()))
                .toList();
    }
}
