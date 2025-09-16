package com.jan_elia.warehouse.service;

import com.jan_elia.warehouse.entities.Category;
import com.jan_elia.warehouse.entities.Product;

import java.time.Clock;
import java.time.LocalDate;
import java.util.List;

public class Warehouse {

    private final Clock clock;

    public Warehouse(Clock clock) {
        this.clock = clock;
        // TODO: add product storage later
    }

    public void addProduct(Product product) { }

    public void updateProduct(String id, String name, Category category, int rating) { }

    public List<Product> getAllProducts() { return null; }

    public Product getProductById(String id) { return null; }

    public List<Product> getProductsByCategorySorted(Category category) { return null; }

    public List<Product> getProductsCreatedAfter(LocalDate date) { return null; }

    public List<Product> getModifiedProducts() { return null; }
}
