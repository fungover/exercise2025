package org.example.service;

import org.example.entities.Category;
import org.example.entities.Product;
import org.example.repository.ProductRepository;

import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class ProductService {

    private final ProductRepository productRepository;

    // Dependency Injection
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // CRUD (delegation)
    public void addProduct(Product product) {
        productRepository.addProduct(product);
    }

    public Optional<Product> getProductById(String id) {
        return productRepository.getProductById(id);
    }

    public List<Product> getAllProducts() {
        return productRepository.getAllProducts();
    }

    //overload
    public void updateProduct(Product product) {
        productRepository.updateProduct(product);
    }
    //overload
    public void updateProduct(String id, String name, Category category, int rating) {
        Optional<Product> existing = productRepository.getProductById(id);

        if (existing.isEmpty()) {
            throw new IllegalArgumentException("Product with ID " + id + " does not exist");
        }

        Product oldProduct = existing.get();

        Product updated = new Product.Builder()
                .id(oldProduct.getId())
                .name(name)
                .category(category)
                .price(oldProduct.getPrice())
                .rating(rating)
                .createdDate(oldProduct.getCreatedDate())
                .modifiedDate(ZonedDateTime.now())
                .build();

        productRepository.updateProduct(updated);
    }
    //Business logic below

    //sorted by getName a-z
    public List<Product> getProductsByCategorySorted(Category category) {
        if (category == null) {
            throw new IllegalArgumentException("Category cannot be null");
        }

        return productRepository.getAllProducts().stream()
                .filter(product -> category.equals(product.getCategory()))
                .sorted(Comparator.comparing(Product::getName))
                .toList();
    }

    public List<Product> getProductsCreatedAfter(ZonedDateTime dateTime) {
        if (dateTime == null) {
            throw new IllegalArgumentException("dateTime cannot be null");
        }
        return productRepository.getAllProducts().stream()
                .filter(product -> product.getCreatedDate().isAfter(dateTime))
                .toList();
    }

    //returns products that have been modified since the given date
    public List<Product> getModifiedProducts() {
        return productRepository.getAllProducts().stream()
                .filter(product -> !product.getCreatedDate().isEqual(product.getModifiedDate()))
                .toList();
    }
}
