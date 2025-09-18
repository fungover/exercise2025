package service;


import entities.Category;
import entities.Product;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class Warehouse {
    private final List<Product> products = new ArrayList<>();

    public void addProduct(Product product) {
        if(product.name().isEmpty()){
            throw new IllegalArgumentException("Name can not be empty");
        }
        products.add(product);

    }

    public List<Product> getProducts() {
        return Collections.unmodifiableList(products);
    }


    public void updateProduct(int id, String name, Category category, int rating) {
        for (int i = 0; i < products.size(); i++) {
            Product p = products.get(i);
            if (p.id() == id) {
                Product updated = new Product(id, name, category, rating, p.createdAt(), LocalDateTime.now());
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
