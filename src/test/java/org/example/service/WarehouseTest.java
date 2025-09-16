package org.example.service;

import org.example.entities.Category;
import org.example.entities.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class WarehouseTest {

    Warehouse warehouse;

    @BeforeEach
    void setUp() {
        warehouse = new Warehouse();
    }

    @Test
    void addProduct() {
        LocalDateTime now = LocalDateTime.now();
        Product product = new Product(
                "1",
                "Harry Potter and the deathly hollows",
                Category.BOOKS,
                9,
                now,
                now);

        warehouse.addProduct(product);

        assertEquals(1, warehouse.getAllProducts().size());
        assertTrue(warehouse.getAllProducts().contains(product));
    }

    @Test
    void addProductEmptyNameThrowsException() {
        LocalDateTime now = LocalDateTime.now();
        Product product = new Product(
                "2",
                "",
                Category.BOOKS,
                9,
                now,
                now
        );
        assertThrows(IllegalArgumentException.class, () -> warehouse.addProduct(product));
    }

    @Test
    void updateProduct() {
        LocalDateTime now = LocalDateTime.now();
        Product product = new Product(
                "1",
                "Harry Potter and the deathly hollows",
                Category.BOOKS,
                9,
                now,
                now
        );
        warehouse.addProduct(product);

        warehouse.updateProduct("1", "HOKA One One", Category.SHOES, 10);

        Product updatedProduct = warehouse.getProductById("1");
        assertEquals("HOKA One One", updatedProduct.name());
        assertEquals(Category.SHOES, updatedProduct.category());
        assertEquals(10, updatedProduct.rating());
    }

    @Test
    void updateProductIdThatDontExistThrowsException() {

        assertThrows(IllegalArgumentException.class, () ->
                warehouse.updateProduct("99", "eleventyelevenonetyone", Category.BOOKS, 5)
        );
    }
}