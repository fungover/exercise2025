package service;

import entities.Category;
import entities.Product;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;


public class ProductService implements ProductRepository{

    public void addProduct(Product product) {
        if (product.name().isEmpty()) {
            throw new IllegalArgumentException("Name can not be empty");
        }
        products.add(product);

    }

    @Override
    public Optional<Product> getProductById(String id) {
        return Optional.empty();
    }

    @Override
    public List<Product> getAllProducts() {
        return List.of();
    }

    @Override
    public void updateProduct(Product product) {

    }

    public List<Product> getProducts() {
        return Collections.unmodifiableList(products);
    }


    public void updateProduct(int id, String name, Category category, int rating) {
        for (int i = 0; i < products.size(); i++) {
            Product p = products.get(i);
            if (p.id() == id) {
                Product updated = new Product.Builder()
                        .id(id)
                        .name(name)
                        .category(category)
                        .rating(rating)
                        .createdAt(LocalDateTime.now())
                        .modifiedAt(LocalDateTime.now())
                        .build();

                products.set(i, updated);
                return;
            }
        }
        throw new NoSuchElementException("Product with id " + id + " not found");
    }

    public List<Product> getProductsByCategorySorted(Category category) {
        return products.stream()
                .filter(p -> p.category() == category)
                .sorted(Comparator.comparing(Product::name))
                .toList();

    }

    public List<Product> getProductsCreatedAfter(LocalDate date) {
        return products.stream()
                .filter(p -> p.createdAt().toLocalDate().isAfter(date))
                .toList();
    }

    public List<Product> getModifiedProducts() {
        return products.stream()
                .filter(p -> !p.createdAt().equals(p.modifiedAt()))
                .toList();
    }
}
