package org.example.service;

import org.example.entities.CategoryEnum;
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

    public Boolean addProduct(Product product) {
        return productRepository.addProduct(product);
    }

    public List<Product> getProducts() {
        return productRepository.getAllProducts();
    }

    public boolean updateProduct(String id, String name, CategoryEnum category, int rating) {
        Objects.requireNonNull(name, "Name can't be null");
        Objects.requireNonNull(category, "Category can't be null");

        Optional<Product> searchingProduct = productRepository.getProduct(id);

        if (searchingProduct.isPresent()) {
            Product old = searchingProduct.get();
            Product updated = new Product.Builder()
                .id(old.id())
                .name(name)
                .category(category)
                .rating(rating)
                .createdDate(old.createdDate())
                .modifiedDate(LocalDate.now())
                .build();

            productRepository.updateProduct(updated);
            return true;
        }

        return false;
    }

    public Optional<Product> getProductById(String id) {
        return productRepository.getProduct(id);
    }

    public List<Product> getProductsByCategorySorted(CategoryEnum category) {
        return warehouseProducts.stream()
                .filter(product -> product.category() == category)
                .sorted(Comparator.comparing(product -> product.name() == null ? "" : product.name().trim(), String.CASE_INSENSITIVE_ORDER))
                .collect(Collectors.toList());
    }

    public List<Product> getProductsCreatedAfter(LocalDate date) {
        return warehouseProducts.stream()
                .filter(product -> product.createdDate().isAfter(date))
                .collect(Collectors.toList());
    }

    public List<Product> getModifiedProducts() {
        return warehouseProducts.stream()
                .filter(product -> !Objects.equals(product.createdDate(), product.modifiedDate()))
                .collect(Collectors.toList());
    }
}
