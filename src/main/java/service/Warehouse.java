package service;


import entities.Category;
import entities.Product;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collector;

import static entities.Category.BOOK;

public class Warehouse {
    private final List<Product> products = new ArrayList<>();

    public void addProduct(Product product) {
        if(product.getName().isEmpty()){
            throw new IllegalArgumentException("Name can not be empty");
        }
        products.add(product);

    }

    public List<Product> getProducts() {
        return products;
    }


    public void updateProduct(int id, String name, Category category, int rating) {
        Product product = products.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException(
                        "Product with id " + id + " not found"));
        product.setName(name);
        product.setCategory(category);
        product.setRating(rating);

    }

    public List<Product> getAllProducts() {
        return new ArrayList<>(products);
    }

    public List<Product> getProductsByCategorySorted(Category category) {
        return products.stream()
                .filter(p -> p.getCategory() == category)
                .sorted(Comparator.comparing(Product::getName))
                .toList();

    }

    public List<Product> getProductsCreatedAfter(LocalDate date) {
        return products.stream()
                .filter(p -> p.getCreatedAt().toLocalDate().isAfter(date))
                .toList();
    }

    public List<Product> getModifiedProducts() {
            return products.stream()
                    .filter(p -> !p.getCreatedAt().equals(p.getModifiedAt()))
                    .toList();
        }
    }
