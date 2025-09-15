package org.example.repository;

import org.example.entities.Category;
import org.example.entities.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

public class InMemoryProductRepository implements ProductRepository {
    private final List<Product> products = new ArrayList<>();

    @Override
    public void addProduct(Product product) {
        if (product == null || product.getName().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be null or blank");
        }
        if (product.getCategory() == null) {
            throw new IllegalArgumentException("Category cannot be null");
        }
        if (product.getRating() < 1 || product.getRating() > 10) {
            throw new IllegalArgumentException("Rating must be between 1 and 10");
        }
        if (products.stream().anyMatch(p -> p.getId().equals(product.getId()))) {
            throw new IllegalArgumentException("Product with id " + product.getId() + " already exists");
        }
        if (products.stream().anyMatch(p -> p.getName().equalsIgnoreCase(product.getName()))) {
            throw new IllegalArgumentException("Product with name " + product.getName() + " already exists");
        }
        products.add(product);
    }

    @Override
    public Optional<Product> getProductById(String id) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Product id cannot be null or blank");
        }

        return products.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Product> getAllProducts() {
        return new ArrayList<>(products);
    }

    @Override
    public void updateProduct(Product product) {
        int index = IntStream.range(0, products.size())
                .filter(i -> products.get(i).getId().equals(product.getId()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        Product existingProduct = products.get(index);
        Product updatedProduct = existingProduct.update(product.getName(), product.getCategory(), product.getRating());

        products.set(index, updatedProduct);
    }
}
