package org.example.service;

import org.example.entities.Category;
import org.example.entities.Product;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

public class WarehouseConcurrencyTest {

    @Test
    void addProduct_ConcurrentDuplicateIds_PreventRaceCondition() throws InterruptedException {
        // Given
        Warehouse warehouse = new Warehouse();
        ExecutorService executor = Executors.newFixedThreadPool(50);
        CountDownLatch latch = new CountDownLatch(100);
        CountDownLatch startGate = new CountDownLatch(1);
        AtomicInteger successCount = new AtomicInteger(0);
        AtomicInteger duplicateCount = new AtomicInteger(0);

        // When 100 threads try to add products with same ID
        for (int i = 0; i < 100; i++) {
            executor.submit(() -> {
                try {
                    startGate.await();
                    Product product = new Product(
                        "DUPLICATE_ID", // Same ID for all threads
                        "Test Product",
                        Category.ELECTRONICS,
                        5,
                        LocalDate.now(),
                        LocalDate.now()
                    );
                    warehouse.addProduct(product);
                    successCount.incrementAndGet();
                } catch (IllegalArgumentException e) {
                    if (e.getMessage().contains("already exists")) {
                        duplicateCount.incrementAndGet();
                    }
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    fail("Interrupted while waiting for start: " + ie.getMessage());
                } finally {
                    latch.countDown();
                }
            });
        }

        // Fire the start gun and wait for completion
        startGate.countDown();
        assertTrue(latch.await(15, TimeUnit.SECONDS), "Timed out waiting for tasks to complete");
        executor.shutdown();
        assertTrue(executor.awaitTermination(10, TimeUnit.SECONDS), "Executor did not terminate in time");

        // Then - Only one should succeed, 99 should fail with duplicate error
        assertEquals(1, successCount.get(), "Only one thread should successfully add the product");
        assertEquals(99, duplicateCount.get(), "99 threads should fail with duplicate ID error");
        assertEquals(1, warehouse.getAllProducts().size(), "Warehouse should contain exactly one product");
        assertEquals("DUPLICATE_ID", warehouse.getAllProducts().get(0).id());
    }

    @Test
    void addProduct_ConcurrentUniqueIds_AllSucceed() throws InterruptedException {
        // Given
        Warehouse warehouse = new Warehouse();
        ExecutorService executor = Executors.newFixedThreadPool(20);
        CountDownLatch latch = new CountDownLatch(50);
        CountDownLatch startGate = new CountDownLatch(1);
        AtomicInteger successCount = new AtomicInteger(0);

        // When - 50 threads add products with unique IDs
        for (int i = 0; i < 50; i++) {
            final int productId = i;
            executor.submit(() -> {
                try {
                    startGate.await();
                    Product product = new Product(
                        "UNIQUE_ID_" + productId,
                        "Product " + productId,
                        Category.ELECTRONICS,
                        5,
                        LocalDate.now(),
                        LocalDate.now()
                    );
                    warehouse.addProduct(product);
                    successCount.incrementAndGet();
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    fail("Interrupted while waiting for start: " + ie.getMessage());
                } catch (Exception e) {
                    // Should not happen with unique IDs
                    fail("Unexpected exception: " + e.getMessage());
                } finally {
                    latch.countDown();
                }
            });
        }

        // Fire the start gun and wait for completion
        startGate.countDown();
        assertTrue(latch.await(15, TimeUnit.SECONDS), "Timed out waiting for tasks to complete");
        executor.shutdown();
        assertTrue(executor.awaitTermination(10, TimeUnit.SECONDS), "Executor did not terminate in time");

        // Then - All should succeed
        assertEquals(50, successCount.get(), "All threads should successfully add their products");
        assertEquals(50, warehouse.getAllProducts().size(), "Warehouse should contain all 50 products");
    }

    @Test
    void updateProduct_ConcurrentUpdates_AtomicOperation() throws InterruptedException {
        // Given
        Warehouse warehouse = new Warehouse();
        Product initialProduct = new Product(
            "CONCURRENT_UPDATE",
            "Initial Product",
            Category.ELECTRONICS,
            5,
            LocalDate.now().minusDays(1),
            LocalDate.now().minusDays(1)
        );
        warehouse.addProduct(initialProduct);

        ExecutorService executor = Executors.newFixedThreadPool(20);
        CountDownLatch latch = new CountDownLatch(100);
        CountDownLatch startGate = new CountDownLatch(1);
        AtomicInteger successCount = new AtomicInteger(0);

        // When 100 threads try to update the same product concurrently
        for (int i = 0; i < 100; i++) {
            final int updateId = i;
            executor.submit(() -> {
                try {
                    startGate.await();
                    warehouse.updateProduct(
                        "CONCURRENT_UPDATE",
                        "Updated Product " + updateId,
                        Category.GAMES,
                        updateId % 11 // 0-10 rating range
                    );
                    successCount.incrementAndGet();
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    fail("Interrupted while waiting for start: " + ie.getMessage());
                } catch (Exception e) {
                    fail("Unexpected exception during concurrent update: " + e.getMessage());
                } finally {
                    latch.countDown();
                }
            });
        }

        // Fire the start gun and wait for completion
        startGate.countDown();
        assertTrue(latch.await(15, TimeUnit.SECONDS), "Timed out waiting for tasks to complete");
        executor.shutdown();
        assertTrue(executor.awaitTermination(10, TimeUnit.SECONDS), "Executor did not terminate in time");

        // Then - All updates should succeed, final state should be consistent
        assertEquals(100, successCount.get(), "All update operations should succeed");
        assertEquals(1, warehouse.getAllProducts().size(), "Should still have exactly one product");

        Product finalProduct = warehouse.getProductById("CONCURRENT_UPDATE").get();
        assertTrue(finalProduct.name().startsWith("Updated Product"));
        assertEquals(Category.GAMES, finalProduct.category());
        assertTrue(finalProduct.rating() >= 0 && finalProduct.rating() <= 10);
        assertNotEquals(initialProduct.modifiedDate(), finalProduct.modifiedDate());
    }
}