package org.example;

import org.example.entities.CategoryEnum;
import org.example.entities.Product;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Warehouse {
    private final List<Product> warehouseProducts = new ArrayList<>();

    public void addProduct(Product product) {
        warehouseProducts.add(product);
    }

    public void getProducts() {
        warehouseProducts.stream().forEach(System.out::println);
    }
    public boolean updateProduct(int id, String name, CategoryEnum category, int rating) {
        Objects.requireNonNull(name, "Name can't be null");
        Objects.requireNonNull(category, "Category can't be null");

        for (int i = 0; i < warehouseProducts.size(); i++) {
            Product product = warehouseProducts.get(i);
            if (product.id() == id) {
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
}
