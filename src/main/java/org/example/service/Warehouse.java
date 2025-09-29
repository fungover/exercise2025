package org.example.service;

import org.example.entities.CategoryEnum;
import org.example.entities.Product;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class Warehouse {
    private final List<Product> warehouseProducts = new ArrayList<>();

    public void addProduct(Product product) {
        warehouseProducts.add(product);
    }

    public void getProducts() {
        warehouseProducts.stream().forEach(System.out::println);
    }
    public boolean updateProduct(String id, String name, CategoryEnum category, int rating) {
        Objects.requireNonNull(name, "Name can't be null");
        Objects.requireNonNull(category, "Category can't be null");

        for (int i = 0; i < warehouseProducts.size(); i++) {
            Product product = warehouseProducts.get(i);
            if (Objects.equals(product.id().toString(), id)) {
                Product updated = new Product(
                        product.id(),
                        name,
                        category,
                        rating,
                        product.createdDate(),
                        LocalDate.now()
                );
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

    public List<Product> getProductsByCategory(CategoryEnum category) {
        return warehouseProducts.stream()
                .filter(product -> product.category() == category)
                .collect(Collectors.toList());
    }

    public List<Product> getProductsCreatedAfter(LocalDate date) {
        return warehouseProducts.stream()
                .filter(product -> product.createdDate().isAfter(date))
                .collect(Collectors.toList());
    }

    public List<Product> getModifiedProducts(LocalDate date) {
        return warehouseProducts.stream()
                .filter(product -> product.createdDate() != product.modifiedDate())
                .collect(Collectors.toList());
    }
}
