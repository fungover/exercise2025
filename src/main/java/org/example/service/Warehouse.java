package org.example.service;

import org.example.entities.CategoryEnum;
import org.example.entities.Product;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class Warehouse {
    private final List<Product> warehouseProducts = new ArrayList<>();

    public Boolean addProduct(Product product) {
        return warehouseProducts.add(product);
    }

    public List<Product> getProducts() {
        return new ArrayList<>(warehouseProducts);
    }

    public boolean updateProduct(String id, String name, CategoryEnum category, int rating) {
        Objects.requireNonNull(name, "Name can't be null");
        Objects.requireNonNull(category, "Category can't be null");

        for (int i = 0; i < warehouseProducts.size(); i++) {
            Product product = warehouseProducts.get(i);
            if (Objects.equals(product.id().toString(), id)) {
                Product updated = new Product.Builder()
                        .id(product.id())
                        .name(name)
                        .category(category)
                        .rating(rating)
                        .createdDate(product.createdDate())
                        .modifiedDate(LocalDate.now())
                        .build();

                warehouseProducts.set(i, updated);
                return true;
            }
        }
        return false;
    }

    public Optional<Product> getProductById(String id) {
        return warehouseProducts.stream()
                .filter(product -> Objects.equals(product.id().toString(), id))
                .findFirst();
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
