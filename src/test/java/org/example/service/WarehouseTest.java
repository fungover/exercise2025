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

    @Test
    void shouldGetProductsByCategorySortedByName() {
        Product electronics1 = new Product("1","Nokia 3310", Category.ELECTRONICS, 8, LocalDate.now());
        Product electronics2 = new Product("2", "Apple iMac G3", Category.ELECTRONICS, 2, LocalDate.now());
        Product sports = new Product("3", "Football - World Cup 1994", Category.SPORTS, 10, LocalDate.now());

        warehouse.addProduct(electronics1);
        warehouse.addProduct(electronics2);
        warehouse.addProduct(sports);

        List<Product> electronicsProducts = warehouse.getProductsByCategorySorted(Category.ELECTRONICS);

        assertEquals(2, electronicsProducts.size());
        assertEquals("Apple iMac G3", electronicsProducts.get(0).name());
        assertEquals("Nokia 3310", electronicsProducts.get(1).name());
    }

    @Test
    void shouldGetProductsCreatedAfterGivenDate() {
        LocalDate cutoffDate = LocalDate.of(2025, 9, 8);
        Product oldProduct = new Product("1", "Test product", Category.ELECTRONICS, 3, LocalDate.of(2025, 9, 1));
        Product newProduct1 = new Product("2", "New Product 1", Category.ELECTRONICS, 8, LocalDate.of(2025, 9, 10));
        Product newProduct2 = new Product("3", "New Product 2", Category.SPORTS, 5, LocalDate.of(2025, 9, 15));

        warehouse.addProduct(oldProduct);
        warehouse.addProduct(newProduct1);
        warehouse.addProduct(newProduct2);

        List<Product> recentProducts = warehouse.getProductsCreatedAfter(cutoffDate);

        assertEquals(2, recentProducts.size());
        assertTrue(recentProducts.contains(newProduct1));
        assertTrue(recentProducts.contains(newProduct2));
        assertFalse(recentProducts.contains(oldProduct));
    }

    @Test
    void shouldGetModifiedProducts() {
        LocalDate createdDate = LocalDate.of(2025, 9, 8);
        LocalDate modifiedDate = LocalDate.of(2025, 9, 15);

        Product unmodifiedProduct = new Product("1", "Unmodified", Category.ELECTRONICS, 6, createdDate);
        Product modifiedProduct = new Product("2", "Modified", Category.SPORTS, 7, createdDate, modifiedDate);

        warehouse.addProduct(unmodifiedProduct);
        warehouse.addProduct(modifiedProduct);

        List<Product> modifiedProducts = warehouse.getModifiedProducts();

        assertEquals(1, modifiedProducts.size());
        assertTrue(modifiedProducts.contains(modifiedProduct));
        assertFalse(modifiedProducts.contains(unmodifiedProduct));
    }

    @Test
    void shouldUpdateExistingProduct() {
        Product originalProduct = new Product("1", "Old Product", Category.ELECTRONICS, 8, LocalDate.of(2025, 9, 1));

        warehouse.addProduct(originalProduct);

        warehouse.updateProduct("1", "New Product", Category.SPORTS, 5);

        Optional<Product> updated = warehouse.getProductById("1");

        assertTrue(updated.isPresent());
        assertEquals("New Product", updated.get().name());
        assertEquals(Category.SPORTS, updated.get().category());
        assertEquals(5, updated.get().rating());
        assertEquals(LocalDate.of(2025, 9, 1), updated.get().createdDate());
        assertNotEquals(originalProduct.createdDate(), updated.get().modifiedDate());
    }

    @Test
    void shouldThrowExceptionWhenUpdatingNonExistentProduct() {
        assertThrows(IllegalArgumentException.class, () ->
                warehouse.updateProduct("999", "Name", Category.ELECTRONICS, 5));
    }
}
