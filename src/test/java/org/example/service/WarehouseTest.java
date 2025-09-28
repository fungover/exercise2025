package org.example.service;

import org.example.entities.Category;
import org.example.entities.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class WarehouseTest {

    private Warehouse warehouse;
    private Product testProduct;

    @BeforeEach
    void setUp() {
        warehouse = new Warehouse();
        testProduct = new Product.Builder()
            .id("1")
            .name("iPhone 15")
            .category(Category.ELECTRONICS)
            .rating(9)
            .createdDate(LocalDate.now())
            .modifiedDate(LocalDate.now())
            .build();
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
        Product productWithEmptyId = new Product.Builder()
            .id("   ") // Whitespace-only ID
            .name("iPhone 15")
            .category(Category.ELECTRONICS)
            .rating(9)
            .createdDate(LocalDate.now())
            .modifiedDate(LocalDate.now())
            .build();

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            warehouse.addProduct(productWithEmptyId);
        });
        assertTrue(exception.getMessage().contains("Product ID cannot be empty"));
    }

    @Test
    void addProduct_NullProductId_ThrowsException() {
        // When & Then - Product Builder validation catches null ID
        assertThrows(NullPointerException.class, () -> {
            new Product.Builder()
                .id(null)
                .name("iPhone 15")
                .category(Category.ELECTRONICS)
                .rating(9)
                .createdDate(LocalDate.now())
                .modifiedDate(LocalDate.now())
                .build();
        });
    }

    @Test
    void addProduct_DuplicateId_ThrowsException() {
        // Given
        warehouse.addProduct(testProduct);
        Product duplicateProduct = new Product.Builder()
            .id("1") // Same ID as testProduct
            .name("iPad Pro")
            .category(Category.ELECTRONICS)
            .rating(8)
            .createdDate(LocalDate.now())
            .modifiedDate(LocalDate.now())
            .build();

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
        Product electronics = new Product.Builder().id("1").name("iPhone").category(Category.ELECTRONICS).rating(9).createdDate(LocalDate.now()).modifiedDate(LocalDate.now()).build();
        Product food = new Product.Builder().id("2").name("Apple").category(Category.FOOD).rating(8).createdDate(LocalDate.now()).modifiedDate(LocalDate.now()).build();
        Product anotherElectronics = new Product.Builder().id("3").name("iPad").category(Category.ELECTRONICS).rating(10).createdDate(LocalDate.now()).modifiedDate(LocalDate.now()).build();

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
        Product electronics1 = new Product.Builder().id("1").name("iPhone").category(Category.ELECTRONICS).rating(9).createdDate(LocalDate.now()).modifiedDate(LocalDate.now()).build();
        Product electronics2 = new Product.Builder().id("2").name("iPad").category(Category.ELECTRONICS).rating(10).createdDate(LocalDate.now()).modifiedDate(LocalDate.now()).build();
        Product food = new Product.Builder().id("3").name("Apple").category(Category.FOOD).rating(8).createdDate(LocalDate.now()).modifiedDate(LocalDate.now()).build();

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
        Product iPhone = new Product.Builder().id("1").name("iPhone").category(Category.ELECTRONICS).rating(9).createdDate(LocalDate.now()).modifiedDate(LocalDate.now()).build();
        Product iPad = new Product.Builder().id("2").name("iPad").category(Category.ELECTRONICS).rating(10).createdDate(LocalDate.now()).modifiedDate(LocalDate.now()).build();
        Product apple = new Product.Builder().id("3").name("Apple").category(Category.FOOD).rating(8).createdDate(LocalDate.now()).modifiedDate(LocalDate.now()).build();
        Product banana = new Product.Builder().id("4").name("Banana").category(Category.FOOD).rating(7).createdDate(LocalDate.now()).modifiedDate(LocalDate.now()).build();

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
        Product maxRated1 = new Product.Builder().id("1").name("iPhone").category(Category.ELECTRONICS).rating(10).createdDate(today).modifiedDate(today).build();
        Product maxRated2 = new Product.Builder().id("2").name("iPad").category(Category.ELECTRONICS).rating(10).createdDate(today.minusDays(1)).modifiedDate(today.minusDays(1)).build();
        Product lowerRated = new Product.Builder().id("3").name("Apple").category(Category.FOOD).rating(8).createdDate(today).modifiedDate(today).build();
        Product oldProduct = new Product.Builder().id("4").name("Old Phone").category(Category.ELECTRONICS).rating(10).createdDate(today.minusMonths(2)).modifiedDate(today.minusMonths(2)).build();

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
        Product oldProduct = new Product.Builder().id("1").name("Old Phone").category(Category.ELECTRONICS).rating(10).createdDate(LocalDate.now().minusMonths(2)).modifiedDate(LocalDate.now().minusMonths(2)).build();
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

        // When 100 threads adding products concurrently
        for (int i = 0; i < 100; i++) {
            final int productId = i;
            executor.submit(() -> {
                try {
                    Product product = new Product.Builder()
                        .id(String.valueOf(productId))
                        .name("Product " + productId)
                        .category(Category.ELECTRONICS)
                        .rating(5)
                        .createdDate(LocalDate.now())
                        .modifiedDate(LocalDate.now())
                        .build();
                    warehouse.addProduct(product);
                } finally {
                    latch.countDown();
                }
            });
        }

        
        assertTrue(latch.await(5, TimeUnit.SECONDS), "Timed out waiting for producers to finish");
        executor.shutdown();
        assertTrue(executor.awaitTermination(1, TimeUnit.SECONDS), "Executor did not terminate");

        // Then - All products should be added successfully
        assertEquals(100, warehouse.getAllProducts().size());
        assertEquals(100, warehouse.countProductsInCategory(Category.ELECTRONICS));
    }
}