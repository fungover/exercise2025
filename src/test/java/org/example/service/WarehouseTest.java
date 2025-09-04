package org.example.service;

import org.example.entities.Category;
import org.example.entities.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
        warehouse.addProduct(testProduct); // Add the product to the warehouse

        List<Product> products = warehouse.getAllProducts(); // Retrieve all products to verify the addition
        assertEquals(1, products.size()); // Check that the size of the product list is 1
        assertTrue(products.contains(testProduct)); // Check that the added product is in the list
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when adding product with empty name")
    void addProductWithEmptyName() {

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Product("2", "    ", Category.BOOKS, 5, LocalDate.now(), LocalDate.now())
        );
        assertEquals("Name cannot be empty", exception.getMessage());
    }
}
