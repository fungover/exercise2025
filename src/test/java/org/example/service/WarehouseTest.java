package org.example.service;

import org.example.entities.Category;
import org.example.entities.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.NoSuchElementException;

class WarehouseTest {

    private Warehouse warehouse;
    private Product sampleProduct;

    @BeforeEach
    void setUp() {
        warehouse = new Warehouse();
        sampleProduct = new Product("1", "Laptop", Category.Electronics, 8,
                LocalDate.of(2023, 1, 1), LocalDate.of(2023, 1, 1));
        warehouse.addProduct(sampleProduct);
    }

    @Test
    void testAddProductSuccess() {
        Product newProduct = new Product("2", "Book", Category.Books, 7,
                LocalDate.now(), LocalDate.now());
        warehouse.addProduct(newProduct);
        assertEquals(newProduct, warehouse.getProductById("2"));
    }

    @Test
    void testAddProductWithEmptyNameThrowsException() {
        Product invalidProduct = new Product("3", "", Category.Books, 5,
                LocalDate.now(), LocalDate.now());
        assertThrows(IllegalArgumentException.class, () -> warehouse.addProduct(invalidProduct));
    }

    @Test
    void testUpdateProductSuccess() {
        warehouse.updateProduct("1", "Gaming Laptop", Category.Electronics, 9);
        Product updated = warehouse.getProductById("1");
        assertEquals("Gaming Laptop", updated.getName());
        assertEquals(9, updated.getRating());
        assertNotEquals(updated.getCreatedDate(), updated.getModifiedDate());
    }

    @Test
    void testUpdateNonexistentProductThrowsException() {
        assertThrows(NoSuchElementException.class, () -> {
            warehouse.updateProduct("999", "Name", Category.Food, 5);
        });
    }

    @Test
    void testGetAllProducts() {
        List<Product> all = warehouse.getAllProducts();
        assertEquals(1, all.size());
        assertTrue(all.contains(sampleProduct));
    }

    @Test
    void testGetProductByIdSuccess() {
        Product found = warehouse.getProductById("1");
        assertEquals(sampleProduct, found);
    }

    @Test
    void testGetProductByIdFailure() {
        assertThrows(NoSuchElementException.class, () -> warehouse.getProductById("999"));
    }

    @Test
    void testGetProductsByCategorySorted() {
        Product second = new Product("2", "Camera", Category.Electronics, 6,
                LocalDate.now(), LocalDate.now());
        warehouse.addProduct(second);
        List<Product> electronics = warehouse.getProductsByCategorySorted(Category.Electronics);
        assertEquals(2, electronics.size());
        assertEquals("Camera", electronics.get(0).getName());
    }

    @Test
    void testGetProductsCreatedAfter() {
        Product recent = new Product("3", "Tablet", Category.Electronics, 6,
                LocalDate.of(2025, 1, 1), LocalDate.of(2025, 1, 1));
        warehouse.addProduct(recent);
        List<Product> recentProducts = warehouse.getProductsCreatedAfter(LocalDate.of(2024, 12, 31));
        assertEquals(1, recentProducts.size());
        assertEquals("Tablet", recentProducts.get(0).getName());
    }

    @Test
    void testGetModifiedProducts() {
        warehouse.updateProduct("1", "Updated Laptop", Category.Electronics, 9);
        List<Product> modified = warehouse.getModifiedProducts();
        assertEquals(1, modified.size());
        assertEquals("Updated Laptop", modified.get(0).getName());
    }
}
