package org.example.service;

import org.example.entities.Category;
import org.example.entities.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class WarehouseTest {

    private Warehouse warehouse;
    private Product testProduct;

    @BeforeEach
    void setUp() {
        warehouse = new Warehouse();
        testProduct = new Product(
            "1",
            "iPhone 15",
            Category.ELECTRONICS,
            9,
            LocalDate.now(),
            LocalDate.now()
        );
    }

    @Test
    void addProduct_Success() {
        // When
        warehouse.addProduct(testProduct);

        // Then
        List<Product> products = warehouse.getAllProducts();
        assertEquals(1, products.size());
        assertEquals("iPhone 15", products.get(0).name());
    }

    @Test
    void addProduct_NullProduct_ThrowsException() {
        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            warehouse.addProduct(null);
        });
    }

    @Test
    void getProductById_Success() {
        // Given
        warehouse.addProduct(testProduct);

        // When
        Optional<Product> found = warehouse.getProductById("1");

        // Then
        assertTrue(found.isPresent());
        assertEquals("iPhone 15", found.get().name());
    }

    @Test
    void getProductById_NotFound() {
        // When
        Optional<Product> found = warehouse.getProductById("999");

        // Then
        assertFalse(found.isPresent());
    }

    @Test
    void getAllProducts_EmptyWarehouse() {
        // When
        List<Product> products = warehouse.getAllProducts();

        // Then
        assertTrue(products.isEmpty());
    }

    // VG Distinction Method Tests

    @Test
    void getCategoriesWithProducts_Success() {
        // Given
        Product electronics = new Product("1", "iPhone", Category.ELECTRONICS, 9, LocalDate.now(), LocalDate.now());
        Product food = new Product("2", "Apple", Category.FOOD, 8, LocalDate.now(), LocalDate.now());
        Product anotherElectronics = new Product("3", "iPad", Category.ELECTRONICS, 10, LocalDate.now(), LocalDate.now());

        warehouse.addProduct(electronics);
        warehouse.addProduct(food);
        warehouse.addProduct(anotherElectronics);

        // When
        List<Category> categories = warehouse.getCategoriesWithProducts();

        // Then
        assertEquals(2, categories.size());
        assertTrue(categories.contains(Category.ELECTRONICS));
        assertTrue(categories.contains(Category.FOOD));
    }

    @Test
    void getCategoriesWithProducts_EmptyWarehouse() {
        // When
        List<Category> categories = warehouse.getCategoriesWithProducts();

        // Then
        assertTrue(categories.isEmpty());
    }

    @Test
    void countProductsInCategory_Success() {
        // Given
        Product electronics1 = new Product("1", "iPhone", Category.ELECTRONICS, 9, LocalDate.now(), LocalDate.now());
        Product electronics2 = new Product("2", "iPad", Category.ELECTRONICS, 10, LocalDate.now(), LocalDate.now());
        Product food = new Product("3", "Apple", Category.FOOD, 8, LocalDate.now(), LocalDate.now());

        warehouse.addProduct(electronics1);
        warehouse.addProduct(electronics2);
        warehouse.addProduct(food);

        // When
        long electronicsCount = warehouse.countProductsInCategory(Category.ELECTRONICS);
        long foodCount = warehouse.countProductsInCategory(Category.FOOD);
        long clothingCount = warehouse.countProductsInCategory(Category.CARS);

        // Then
        assertEquals(2, electronicsCount);
        assertEquals(1, foodCount);
        assertEquals(0, clothingCount);
    }

    @Test
    void countProductsInCategory_NullCategory() {
        // When
        long count = warehouse.countProductsInCategory(null);

        // Then
        assertEquals(0, count);
    }

    @Test
    void getProductInitialsMap_Success() {
        // Given
        Product iPhone = new Product("1", "iPhone", Category.ELECTRONICS, 9, LocalDate.now(), LocalDate.now());
        Product iPad = new Product("2", "iPad", Category.ELECTRONICS, 10, LocalDate.now(), LocalDate.now());
        Product apple = new Product("3", "Apple", Category.FOOD, 8, LocalDate.now(), LocalDate.now());
        Product banana = new Product("4", "Banana", Category.FOOD, 7, LocalDate.now(), LocalDate.now());

        warehouse.addProduct(iPhone);
        warehouse.addProduct(iPad);
        warehouse.addProduct(apple);
        warehouse.addProduct(banana);

        // When
        Map<Character, Integer> initialsMap = warehouse.getProductInitialsMap();

        // Then
        assertEquals(3, initialsMap.size());
        assertEquals(2, initialsMap.get('I'));
        assertEquals(1, initialsMap.get('A'));
        assertEquals(1, initialsMap.get('B'));
    }

    @Test
    void getProductInitialsMap_EmptyWarehouse() {
        // When
        Map<Character, Integer> initialsMap = warehouse.getProductInitialsMap();

        // Then
        assertTrue(initialsMap.isEmpty());
    }

    @Test
    void getTopRatedProductsThisMonth_Success() {
        // Given
        LocalDate today = LocalDate.now();
        Product maxRated1 = new Product("1", "iPhone", Category.ELECTRONICS, 10, today, today);
        Product maxRated2 = new Product("2", "iPad", Category.ELECTRONICS, 10, today.minusDays(1), today.minusDays(1));
        Product lowerRated = new Product("3", "Apple", Category.FOOD, 8, today, today);
        Product oldProduct = new Product("4", "Old Phone", Category.ELECTRONICS, 10, today.minusMonths(2), today.minusMonths(2));

        warehouse.addProduct(maxRated1);
        warehouse.addProduct(maxRated2);
        warehouse.addProduct(lowerRated);
        warehouse.addProduct(oldProduct);

        // When
        List<Product> topRated = warehouse.getTopRatedProductsThisMonth();

        // Then
        assertEquals(2, topRated.size());
        assertEquals("iPhone", topRated.get(0).name());  
        assertEquals("iPad", topRated.get(1).name());
        assertTrue(topRated.stream().allMatch(p -> p.rating() == 10));
    }

    @Test
    void getTopRatedProductsThisMonth_NoProductsThisMonth() {
        // Given
        Product oldProduct = new Product("1", "Old Phone", Category.ELECTRONICS, 10, LocalDate.now().minusMonths(2), LocalDate.now().minusMonths(2));
        warehouse.addProduct(oldProduct);

        // When
        List<Product> topRated = warehouse.getTopRatedProductsThisMonth();

        // Then
        assertTrue(topRated.isEmpty());
    }
}