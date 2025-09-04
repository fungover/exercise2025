package org.example.service;

import org.example.entities.Category;
import org.example.entities.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class WarehouseTest {

    private Warehouse warehouse;
    private Product product;

    @BeforeEach
    void setUp() {
        warehouse = new Warehouse();

    }

    // ========================================
    // TESTS FOR addProduct(Product product)
    // ========================================

    @Test
    @DisplayName("Should add product successfully")
    void addProductSuccessfully() {

        Product testProduct = Warehouse.createProduct("1", "Test Product", Category.ELECTRONICS, 8);
        warehouse.addProduct(testProduct);

        List<Product> products = warehouse.getAllProducts();
        assertEquals(1, products.size());
        assertTrue(products.contains(testProduct));
    }
}
