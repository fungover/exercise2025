package com.jan_elia.warehouse.service;

import com.jan_elia.warehouse.entities.Category;
import com.jan_elia.warehouse.entities.Product;

import java.time.Clock;
import java.time.LocalDate;
import java.util.List;

public class Warehouse {

    // constructor
    public Warehouse(Clock clock) {
        // TODO: save the clock
        // TODO: create an internal map for products
    }

    // TODO: addProduct(Product product)
    public void addProduct(Product product) { }

    // TODO: updateProduct(String id, String name, Category category, int rating)
    public void updateProduct(String id, String name, Category category, int rating) { }

    // TODO: getAllProducts()
    public List<Product> getAllProducts() { return null; }

    // TODO: getProductById(String id)
    public Product getProductById(String id) { return null; }

    // TODO: getProductsByCategorySorted(Category category)
    public List<Product> getProductsByCategorySorted(Category category) { return null; }

    // TODO: getProductsCreatedAfter(LocalDate date)
    public List<Product> getProductsCreatedAfter(LocalDate date) { return null; }

    // TODO: getModifiedProducts()
    public List<Product> getModifiedProducts() { return null; }
}