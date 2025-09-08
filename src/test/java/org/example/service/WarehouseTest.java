package org.example.service;

import org.example.entities.Category;
import org.example.entities.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class WarehouseTest {

    private Warehouse warehouse;
    private Clock fixed;

    @BeforeEach
    void setup() {
        // Europe/Stockholm, 2025-09-08 12:00 local (10:00Z)
        fixed = Clock.fixed(Instant.parse("2025-09-08T10:00:00Z"), ZoneId.of("Europe/Stockholm"));
        warehouse = new Warehouse(fixed);
    }

    /** getAllProducts method */
    @Test
    void getAllProducts_returnsEmptyWhenNothingAdded() {
        var all = warehouse.getAllProducts();
        assertTrue(all.isEmpty());
    }

    @Test
    void getAllProducts_listsTwoAfterAddingTwo() {
        LocalDateTime createdAt1 = LocalDateTime.of(2025, 9, 1, 12, 0);
        LocalDateTime createdAt2 = LocalDateTime.of(2025, 9, 2, 12, 0);

        Product testProduct1 = Product.createNew("id1", "Product1", Category.BOOKS, 7, createdAt1);
        Product testProduct2 = Product.createNew("id2", "Product2", Category.ELECTRONICS, 8, createdAt2);

        warehouse.addProduct(testProduct1);
        warehouse.addProduct(testProduct2);

        var all = warehouse.getAllProducts();
        assertEquals(2, all.size());
        assertTrue(all.contains(testProduct1));
        assertTrue(all.contains(testProduct2));
    }

    /** addProduct method */
    @Test
    void addProduct_success_addsToWarehouse() {
        LocalDateTime createdAt = LocalDateTime.of(2025, 9, 1, 12, 0);
        Product testProduct = Product.createNew("id1", "Product1", Category.BOOKS, 7, createdAt);

        warehouse.addProduct(testProduct);

        var all = warehouse.getAllProducts();
        assertEquals(1, all.size());
        assertTrue(all.contains(testProduct));
    }

    @Test
    void addProduct_duplicateId_throwsIllegalArgumentException() {
        LocalDateTime createdAt1 = LocalDateTime.of(2025, 9, 1, 12, 0);
        LocalDateTime createdAt2 = LocalDateTime.of(2025, 9, 2, 12, 0);

        Product testProduct1 = Product.createNew("id1", "Product1", Category.BOOKS, 5, createdAt1);
        Product testProduct2 = Product.createNew("id1", "Product2", Category.TOYS, 8, createdAt2);

        warehouse.addProduct(testProduct1);

        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> warehouse.addProduct(testProduct2));
        assertTrue(exception.getMessage().contains("duplicate id"));
    }

    /** getProductById method */
    @Test
    void getProductById_success_getCorrectProductById() {
        LocalDateTime createdAt = LocalDateTime.of(2025, 9, 1, 12, 0);
        Product testProduct = Product.createNew("id1", "Product1", Category.BOOKS, 7, createdAt);
        warehouse.addProduct(testProduct);

        var testProductById = warehouse.getProductById(testProduct.id());

        assertEquals(Optional.of(testProduct), warehouse.getProductById(testProduct.id()));
    }

    @Test
    void getProductById_nullId_throwsNullPointerException() {
        NullPointerException exception =
                assertThrows(NullPointerException.class, () -> warehouse.getProductById(null));
        assertTrue(exception.getMessage().contains("id"));
    }

    @Test
    void getProductById_blankId_throwsIllegalArgumentException() {
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> warehouse.getProductById("  "));
        assertTrue(exception.getMessage().contains("id required"));
    }
}
