package org.example.service;

import org.example.entities.Category;
import org.example.entities.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

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

    @Test
    void getAllProductsReturnsAllProducts() {
        LocalDateTime now = LocalDateTime.now();

        Product product1 = new Product("1", "Pizza", Category.FOOD, 100, now, now);
        Product product2 = new Product("2", "Tacos", Category.FOOD, 99, now, now);

        warehouse.addProduct(product1);
        warehouse.addProduct(product2);

        List<Product> allProducts = warehouse.getAllProducts();

        assertEquals(2, allProducts.size());
        assertTrue(allProducts.contains(product1));
        assertTrue(allProducts.contains(product2));
    }
}