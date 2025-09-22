package org.example.service;

import org.example.entities.Category;
import org.example.entities.Product;
import org.example.repository.InMemoryProductRepository;
import org.example.repository.ProductRepository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ProductService {
    private final ProductRepository productRepository;
    private final List<Product> products;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
        this.products = productRepository.getAllProducts();
    }

    public List<Product> getProductsByCategorySorted(Category category) {
        return products.stream()
                .filter(product -> product.category().equals(category))
                .sorted(Comparator.comparing(Product::name))
                .toList();
    }

    public List<Product> getProductsCreatedAfter(LocalDateTime date) {
        return products.stream()
                .filter(product -> product.createdDate().isAfter(date))
                .toList();
    }

    public List<Product> getModifiedProducts() {
        return products.stream()
                .filter(product -> product.modifiedDate() != null)
                .toList();
    }

    public Map<Category, Product> getCategoriesWithProducts() {
        return products.stream()
                .collect(Collectors.toMap(
                        Product::category,
                        Function.identity(),
                        (firstProduct, ignored) -> firstProduct
                ));
    }

}
