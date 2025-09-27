package org.example.service;

import org.example.entities.Category;
import org.example.entities.Product;
import org.example.repository.InMemoryProductRepository;
import org.example.repository.ProductRepository;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

public class ProductServiceConcurrencyTest {
    @Test
    void addProduct_ConcurrentDuplicateIds_PreventRaceCondition() throws InterruptedException {
        // Given
        ProductRepository productRepository = new InMemoryProductRepository();
        ProductService productService = new ProductService(productRepository);
        ExecutorService executor = Executors.newFixedThreadPool(50);
        CountDownLatch latch = new CountDownLatch(100);
        AtomicInteger successCount = new AtomicInteger(0);
        AtomicInteger duplicateCount = new AtomicInteger(0);

        // When 100 threads try to add products with same ID
        for (int i = 0; i < 100; i++) {
            executor.submit(() -> {
                try {
                    Product product = new Product.Builder()
                        .id("DUPLICATE_ID") // Same ID for all threads
                        .name("Test Product")
                        .category(Category.ELECTRONICS)
                        .rating(5)
                        .price(new BigDecimal("99.99"))
                        .createdDate(LocalDate.now())
                        .modifiedDate(LocalDate.now())
                        .build();
                    productService.addProduct(product);
                    successCount.incrementAndGet();
                } catch (IllegalArgumentException e) {
                    if (e.getMessage().contains("already exists")) {
                        duplicateCount.incrementAndGet();
                    }
                } finally {
                    latch.countDown();
                }
            });
        }

        // Wait for all threads to complete
        latch.await(10, TimeUnit.SECONDS);
        executor.shutdown();

        // Then - Only one should succeed, 99 should fail with duplicate error
        assertEquals(1, successCount.get(), "Only one thread should successfully add the product");
        assertEquals(99, duplicateCount.get(), "99 threads should fail with duplicate ID error");
        assertEquals(1, productService.getAllProducts().size(), "Repository should contain exactly one product");
        assertEquals("DUPLICATE_ID", productService.getAllProducts().get(0).id());
    }

    @Test
    void addProduct_ConcurrentUniqueIds_AllSucceed() throws InterruptedException {
        // Given
        ProductRepository productRepository = new InMemoryProductRepository();
        ProductService productService = new ProductService(productRepository);
        ExecutorService executor = Executors.newFixedThreadPool(20);
        CountDownLatch latch = new CountDownLatch(50);
        AtomicInteger successCount = new AtomicInteger(0);

        // When - 50 threads add products with unique IDs
        for (int i = 0; i < 50; i++) {
            final int productId = i;
            executor.submit(() -> {
                try {
                    Product product = new Product.Builder()
                        .id("UNIQUE_ID_" + productId)
                        .name("Product " + productId)
                        .category(Category.ELECTRONICS)
                        .rating(5)
                        .price(new BigDecimal("99.99"))
                        .createdDate(LocalDate.now())
                        .modifiedDate(LocalDate.now())
                        .build();
                    productService.addProduct(product);
                    successCount.incrementAndGet();
                } catch (Exception e) {
                    // Should not happen with unique IDs
                    fail("Unexpected exception: " + e.getMessage());
                } finally {
                    latch.countDown();
                }
            });
        }

        // Wait for all threads to complete
        latch.await(10, TimeUnit.SECONDS);
        executor.shutdown();

        // Then - All should succeed
        assertEquals(50, successCount.get(), "All threads should successfully add their products");
        assertEquals(50, productService.getAllProducts().size(), "Repository should contain all 50 products");
    }

    @Test
    void updateProduct_ConcurrentUpdates_AtomicOperation() throws InterruptedException {
        // Given
        ProductRepository productRepository = new InMemoryProductRepository();
        ProductService productService = new ProductService(productRepository);
        Product initialProduct = new Product.Builder()
            .id("CONCURRENT_UPDATE")
            .name("Initial Product")
            .category(Category.ELECTRONICS)
            .rating(5)
            .price(new BigDecimal("99.99"))
            .createdDate(LocalDate.now().minusDays(1))
            .modifiedDate(LocalDate.now().minusDays(1))
            .build();
        productService.addProduct(initialProduct);

        ExecutorService executor = Executors.newFixedThreadPool(20);
        CountDownLatch latch = new CountDownLatch(100);
        AtomicInteger successCount = new AtomicInteger(0);

        // When 100 threads try to update the same product concurrently
        for (int i = 0; i < 100; i++) {
            final int updateId = i;
            executor.submit(() -> {
                try {
                    productService.updateProduct(
                        "CONCURRENT_UPDATE",
                        "Updated Product " + updateId,
                        Category.GAMES,
                        updateId % 11 // 0-10 rating range
                    );
                    successCount.incrementAndGet();
                } catch (Exception e) {
                    fail("Unexpected exception during concurrent update: " + e.getMessage());
                } finally {
                    latch.countDown();
                }
            });
        }

        // Wait for all threads to complete
        latch.await(10, TimeUnit.SECONDS);
        executor.shutdown();

        // Then - All updates should succeed, final state should be consistent
        assertEquals(100, successCount.get(), "All update operations should succeed");
        assertTrue(
            productService.getAllProducts().stream().anyMatch(product -> product.id().equals("CONCURRENT_UPDATE")),
            "Repository should still contain versions for CONCURRENT_UPDATE"
        );
        Product finalProduct = productService.getProductById("CONCURRENT_UPDATE").get();
        assertTrue(finalProduct.name().startsWith("Updated Product"));
        assertEquals(Category.GAMES, finalProduct.category());
        assertTrue(finalProduct.rating() >= 0 && finalProduct.rating() <= 10);
        assertNotEquals(initialProduct.modifiedDate(), finalProduct.modifiedDate());
    }
}