package org.example.service;

import org.example.entities.Product;

import java.util.ArrayList;

public class Warehouse {
    private final ArrayList<Product> products = new ArrayList<>();

    public void addProduct(Product product) {
        if (product.rating() >= 0 && product.rating() < 10) {
            products.add(product);
        } else {
            System.out.println("Rating must be between 0 and 10");
        }
    }
}
