package org.example.service;
import org.example.entities.Category;
import org.example.entities.Product;
import org.example.repository.ProductRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Comparator;
import java.util.stream.Collectors;

public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void addProduct(Product product) {
        productRepository.addProduct(product);
    }

    public Product getProductById(String id) {
        return productRepository.getProductById(id)
                .orElseThrow(()-> new IllegalArgumentException("Product with ID " + id + " not found"));
    }

    public List<Product> getAllProducts() {
        return productRepository.getAllProducts();
    }

    public void updateProduct(String id, String name, Category category, int rating){
        Product existing = getProductById(id);
        if (existing == null) {
            throw new IllegalArgumentException("Product with ID " + id + " is not found");
        }

        Product updated = new Product.Builder()
                .id(id)
                .name(name)
                .category(category)
                .rating(rating)
                .createdDate(existing.createdDate())
                .modifiedDate(LocalDateTime.now())
                .build();


        productRepository.updateProduct(updated);
    }

    public List<Product> getProductsByCategorySorted(Category category) {
        return productRepository.getAllProducts().stream()
                .filter(p -> p.category() == category)
                .sorted(Comparator.comparing(Product::name))
                .collect(Collectors.toList());
    }

    public List<Product> getProductsCreatedAfter(LocalDateTime dateTime) {
        return productRepository.getAllProducts().stream()
                .filter(p -> p.createdDate().isAfter(dateTime))
                .collect(Collectors.toList());
    }

    public List<Product> getModifiedProducts() {
        return productRepository.getAllProducts().stream()
                .filter(p -> !p.createdDate().equals(p.modifiedDate()))
                .collect(Collectors.toList());
    }
}