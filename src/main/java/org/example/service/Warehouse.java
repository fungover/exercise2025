package org.example.service;

import org.example.entities.Product;

import java.util.ArrayList;

public class Warehouse {
    private final ArrayList<Product> products = new ArrayList<>();

    public void addProduct(Product product) {
        if (product.name().isEmpty()) {
            System.out.println("Product name must not be empty");
        } else {
            products.add(product);
        }
    }

    public ArrayList<Product> getProducts() {
        return products;
    }
}
