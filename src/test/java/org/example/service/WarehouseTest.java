package org.example.service;

import org.example.entities.Category;
import org.example.entities.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

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
    void shouldReturnEmptyListWhenNoCategoryMatches() {
        warehouse.addProduct(new Product("1", "Test product", Category.ELECTRONICS, 8, LocalDate.now()));

        List<Product> result = warehouse.getProductsByCategorySorted(Category.SPORTS);

        assertTrue(result.isEmpty());
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
    void shouldReturnEmptyListWhenNoProductsFoundAfterDate() {
        warehouse.addProduct(new Product("1", "Test product", Category.ELECTRONICS, 3, LocalDate.of(2025, 1, 1)));

        List<Product> result = warehouse.getProductsCreatedAfter(LocalDate.of(2099, 12, 10));

        assertTrue(result.isEmpty());
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
    void shouldReturnEmptyListWhenNoModifiedProducts() {
        warehouse.addProduct(new Product("1", "Test product", Category.ELECTRONICS, 3, LocalDate.of(2025, 1, 1)));

        List<Product> result = warehouse.getModifiedProducts();

        assertTrue(result.isEmpty());
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

    @Test
    void shouldThrowExceptionWhenUpdatingProductWithEmptyName() {
        Product product = new Product("1", "Test product", Category.ELECTRONICS, 8, LocalDate.now());
        warehouse.addProduct(product);

        assertThrows(IllegalArgumentException.class, () ->
                warehouse.updateProduct("1", "", Category.ELECTRONICS, 5));
    }

    @Test
    void shouldCountNumberOfProductsInCategory() {
        warehouse.addProduct(new Product("1", "Test Product 1", Category.ELECTRONICS, 8, LocalDate.now()));
        warehouse.addProduct(new Product("2", "Test Product 2", Category.ELECTRONICS, 8, LocalDate.now()));
        warehouse.addProduct(new Product("3", "Test Product 3", Category.SPORTS, 8, LocalDate.now()));

        int electronicsCount = warehouse.countProductsInCategory(Category.ELECTRONICS);
        int sportsCount = warehouse.countProductsInCategory(Category.SPORTS);
        int clothingCount = warehouse.countProductsInCategory(Category.CLOTHING);

        assertEquals(2, electronicsCount);
        assertEquals(1, sportsCount);
        assertEquals(0, clothingCount);
    }

    @Test
    void shouldGetCategoriesWithProductsIn() {
        warehouse.addProduct(new Product("1", "Test Product 1", Category.ELECTRONICS, 8, LocalDate.now()));
        warehouse.addProduct(new Product("2", "Test Product 2", Category.ELECTRONICS, 8, LocalDate.now()));
        warehouse.addProduct(new Product("3", "Test Product 3", Category.SPORTS, 8, LocalDate.now()));

        Set<Category> categories = warehouse.getCategoriesWithProducts();

        assertEquals(2, categories.size());
        assertTrue(categories.contains(Category.ELECTRONICS));
        assertTrue(categories.contains(Category.SPORTS));
        assertFalse(categories.contains(Category.CLOTHING));
    }

    @Test
    void shouldGetProductsInitialMap() {
        warehouse.addProduct(new Product("1", "Mobile phone", Category.ELECTRONICS, 3, LocalDate.now()));
        warehouse.addProduct(new Product("2", "TV 49-inch", Category.ELECTRONICS, 6, LocalDate.now()));
        warehouse.addProduct(new Product("3", "Basketball", Category.SPORTS, 8, LocalDate.now()));
        warehouse.addProduct(new Product("4", "Blue T-shirt", Category.CLOTHING, 2, LocalDate.now()));

        Map<Character, Integer> initialsMap = warehouse.getProductInitialsMap();

        assertEquals(3, initialsMap.size());
        assertEquals(1, initialsMap.get('M'));
        assertEquals(1, initialsMap.get('T'));
        assertEquals(2, initialsMap.get('B'));
        assertNull(initialsMap.get('Z'));
    }
}
