package org.example.service;

import org.example.entities.Category;
import org.example.entities.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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

    // ========================================
    // TESTS FOR updateProduct()
    // ========================================

    @Test
    @DisplayName("Should update a product successfully")
    void updateProductSuccessfully() {

        Product originalProduct = Warehouse.createOldProduct("1", "Original Product", Category.ELECTRONICS, 8, 3); // Create a product with a created date 3 days ago
        warehouse.addProduct(originalProduct);

        warehouse.updateProduct("1", "Updated Product", Category.BOOKS, 9);

        Optional<Product> updated = warehouse.getProductById("1"); // Retrieve the updated product
        assertTrue(updated.isPresent()); // Check that the product is present
        assertEquals("Updated Product", updated.get().name()); // Verify that the name has been updated
        assertEquals(Category.BOOKS, updated.get().category()); // Verify that the category has been updated
        assertEquals(9, updated.get().rating()); // Verify that the rating has been updated
        assertEquals(originalProduct.createdDate(), updated.get().createdDate()); // Verify that the created date remains unchanged
        assertNotEquals(originalProduct.modifiedDate(), updated.get().modifiedDate()); // Verify that the modified date has been updated
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when updating a non-existent product")
    void updateProductThatDoesNotExist() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> warehouse.updateProduct("999", "New Name", Category.BOOKS, 8) // Attempt to update a product that doesn't exist
        );
        assertEquals("Product with id 999 not found", exception.getMessage()); // Verify the exception message
    }
}
