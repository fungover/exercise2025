package org.example.service;

import org.example.entities.Category;
import org.example.entities.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class WareHouseTest {

    private Warehouse warehouse;

    @BeforeEach void setUp() {
        warehouse = new Warehouse();
    }

    @Test
    @DisplayName("Tests for addProduct method")
    void addProduct() {
        LocalDate today = LocalDate.now();

        // Success case
        Product validProduct = new Product("5", "Valid", Category.FOOD, 5, today, today);
        warehouse.addProduct(validProduct);
        assertTrue(warehouse.getAllProducts().contains(validProduct), "Valid product should be added");

        // Failure: empty name
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                warehouse.addProduct(new Product("1", "", Category.FOOD, 2, today, today)));
        assertEquals("Name cannot be empty", exception.getMessage());

        // Failure: null product
        exception = assertThrows(IllegalArgumentException.class, () ->
                warehouse.addProduct(null));
        assertEquals("Product cannot be null", exception.getMessage());

        // Failure: duplicate ID
        Product duplicateIdProduct = new Product(
                validProduct.id(),
                "Duplicate",
                Category.ELECTRONICS,
                5,
                today,
                today
        );
        exception = assertThrows(IllegalArgumentException.class, () ->
                warehouse.addProduct(duplicateIdProduct));
        assertEquals("Product with this ID already exists", exception.getMessage());
    }

}