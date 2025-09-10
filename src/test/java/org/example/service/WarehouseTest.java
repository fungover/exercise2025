package org.example.service;

import org.example.entities.Category;
import org.example.entities.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class WarehouseTest {

    private Warehouse warehouse;
    private Product product;

    @BeforeEach
    void setUp() {
        warehouse = new Warehouse();
        product = new Product("1", "Test product", Category.ELECTRONICS, 8, LocalDate.now());
    }

    @Test
    void shouldCreateEmptyWarehouse() {
        Warehouse warehouse = new Warehouse();
        assertNotNull(warehouse);
    }

    @Test
    void shouldAddProduct() {
        warehouse.addProduct(product);
        assertDoesNotThrow(() -> warehouse.addProduct(product));
    }

    @Test
    void shouldStoreAddedProduct() {
        Product product = new Product("1", "Test product", Category.ELECTRONICS, 8, LocalDate.now());

        warehouse.addProduct(product);

        List<Product> products = warehouse.getAllProducts();
        assertEquals(1, products.size());
        assertEquals(product, products.getFirst());
    }

    @Test
    void shouldNotAddProductWithEmptyName() {
        Product productWithEmptyName = new Product("1", "", Category.ELECTRONICS, 8, LocalDate.now());

        assertThrows(IllegalArgumentException.class, () -> warehouse.addProduct(productWithEmptyName));
    }

    @Test
    void shouldNotAddProductWithNullName() {
        Product productWithNullName = new Product("1", null, Category.ELECTRONICS, 8, LocalDate.now());

        assertThrows(IllegalArgumentException.class, () -> warehouse.addProduct(productWithNullName));
    }

    @Test
    void shouldFindProductWithId() {
        Product product = new Product("213", "Test product", Category.ELECTRONICS, 8, LocalDate.now());
        warehouse.addProduct(product);

        Optional<Product> foundProduct = warehouse.getProductById("213");

        assertTrue(foundProduct.isPresent());
        assertEquals(product, foundProduct.get());
    }

    @Test
    void shouldReturnEmptyOptionalWhenProductNotFound() {
        Optional<Product> foundProduct = warehouse.getProductById("999");

        assertTrue(foundProduct.isEmpty());
    }
}
