package org.example.service;

import org.example.entities.Category;
import org.example.entities.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

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
    void addProduct_EmptyProductId_ThrowsException() {
        // Given
        Product productWithEmptyId = new Product(
            "   ", // Whitespace-only ID
            "iPhone 15",
            Category.ELECTRONICS,
            9,
            LocalDate.now(),
            LocalDate.now()
        );

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            warehouse.addProduct(productWithEmptyId);
        });
        assertTrue(exception.getMessage().contains("Product ID cannot be empty"));
    }

    @Test
    void addProduct_NullProductId_ThrowsException() {
        // When & Then - Product record validation catches null ID
        assertThrows(NullPointerException.class, () -> {
            new Product(
                null,
                "iPhone 15",
                Category.ELECTRONICS,
                9,
                LocalDate.now(),
                LocalDate.now()
            );
        });
    }

    @Test
    void addProduct_DuplicateId_ThrowsException() {
        // Given
        warehouse.addProduct(testProduct);
        Product duplicateProduct = new Product(
            "1", // Same ID as testProduct
            "iPad Pro",
            Category.ELECTRONICS,
            8,
            LocalDate.now(),
            LocalDate.now()
        );

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            warehouse.addProduct(duplicateProduct);
        });
        assertTrue(exception.getMessage().contains("already exists"));

        // Verify original product is unchanged
        assertEquals("iPhone 15", warehouse.getProductById("1").get().name());
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
        YearMonth currentMonth = YearMonth.now();
        LocalDate day1 = currentMonth.atDay(1);
        LocalDate day2 = currentMonth.atDay(2);
        Product maxRated1 = new Product("1", "iPhone", Category.ELECTRONICS, 10, day2, day2);
        Product maxRated2 = new Product("2", "iPad", Category.ELECTRONICS, 10, day1, day1);
        Product lowerRated = new Product("3", "Apple", Category.FOOD, 8, day2, day2);
        Product oldProduct = new Product("4", "Old Phone", Category.ELECTRONICS, 10, currentMonth.minusMonths(2).atDay(1), currentMonth.minusMonths(2).atDay(1));

        warehouse.addProduct(maxRated1);
        warehouse.addProduct(maxRated2);
        warehouse.addProduct(lowerRated);
        warehouse.addProduct(oldProduct);

        // When
        List<Product> topRated = warehouse.getTopRatedProductsThisMonth();

        // Then
        assertEquals(2, topRated.size());
        Set<String> names = topRated.stream().map(Product::name).collect(Collectors.toSet());
        assertTrue(names.contains("iPhone") && names.contains("iPad") && names.size() == 2);
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

    @Test
    void testConcurrentAccess() throws InterruptedException {
        // --Given
        ExecutorService executor = Executors.newFixedThreadPool(10);
        CountDownLatch latch = new CountDownLatch(100);
        CountDownLatch startGate = new CountDownLatch(1);

        // When 100 threads adding products concurrently
        for (int i = 0; i < 100; i++) {
            final int productId = i;
            executor.submit(() -> {
                try {
                    startGate.await();
                    Product product = new Product(
                        String.valueOf(productId),
                        "Product " + productId,
                        Category.ELECTRONICS,
                        5,
                        LocalDate.now(),
                        LocalDate.now()
                    );
                    warehouse.addProduct(product);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    fail("Interrupted while waiting for start: " + ie.getMessage());
                } finally {
                    latch.countDown();
                }
            });
        }

        // Start and wait for completion
        startGate.countDown();
        assertTrue(latch.await(15, TimeUnit.SECONDS), "Timed out waiting for tasks to complete");
        executor.shutdown();
        assertTrue(executor.awaitTermination(10, TimeUnit.SECONDS), "Executor did not terminate in time");

        // Then - All products should be added successfully
        assertEquals(100, warehouse.getAllProducts().size());
        assertEquals(100, warehouse.countProductsInCategory(Category.ELECTRONICS));
    }
}