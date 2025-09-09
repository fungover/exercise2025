package org.example.service;

import org.example.entities.Category;
import org.example.entities.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.NoSuchElementException;
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

        assertEquals(Optional.of(testProduct), testProductById);
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

    /** updateProduct method */
    @Test
    void updateProduct_success_updatesFields_andPersists() {
        LocalDateTime createdAt = LocalDateTime.of(2025, 9, 1, 9, 0);
        Product original = Product.createNew("id1", "Original", Category.BOOKS, 5, createdAt);
        warehouse.addProduct(original);

        Product updated = warehouse.updateProduct("id1", "New Name", Category.TOYS, 9);

        assertEquals("id1", updated.id());
        assertEquals("New Name", updated.name());
        assertEquals(Category.TOYS, updated.category());
        assertEquals(9, updated.rating());
        assertEquals(createdAt, updated.createdDate());
        assertEquals(LocalDateTime.of(2025, 9, 8, 12, 0), updated.modifiedDate());
        assertEquals(Optional.of(updated), warehouse.getProductById("id1"));
    }

    @Test
    void updateProduct_nullId_throwsNullPointerException() {
        assertThrows(NullPointerException.class,
                () -> warehouse.updateProduct(null, "test", Category.BOOKS, 7));
    }

    @Test
    void updateProduct_blankId_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> warehouse.updateProduct("   ", "test", Category.BOOKS, 7));
    }

    @Test
    void updateProduct_missingId_throwsNoSuchElementException() {
        assertThrows(NoSuchElementException.class,
                () -> warehouse.updateProduct("missing-id", "test", Category.BOOKS, 7));
    }

    /** getProductsByCategorySorted method */
    @Test
    void getProductsByCategorySorted_success_filtersAndSorts() {
        LocalDateTime createdAt1 = LocalDateTime.of(2025, 9, 1, 12, 0);
        LocalDateTime createdAt2 = LocalDateTime.of(2025, 9, 2, 12, 0);
        LocalDateTime createdAt3 = LocalDateTime.of(2025, 9, 3, 12, 0);

        Product testProduct1 = Product.createNew("id1", "C", Category.BOOKS, 5, createdAt1);
        Product testProduct2 = Product.createNew("id2", "B", Category.TOYS, 8, createdAt2);
        Product testProduct3 = Product.createNew("id3", "A", Category.TOYS, 8, createdAt3);

        warehouse.addProduct(testProduct1);
        warehouse.addProduct(testProduct2);
        warehouse.addProduct(testProduct3);

        var result = warehouse.getProductsByCategorySorted(Category.TOYS);

        assertEquals(List.of("A", "B"),
                result.stream().map(Product::name).toList());
    }

    @Test
    void getProductsByCategorySorted_noProductsInCategory_returnsEmptyList() {
        LocalDateTime t1 = LocalDateTime.of(2025, 9, 1, 12, 0);
        LocalDateTime t2 = LocalDateTime.of(2025, 9, 2, 12, 0);

        Product product1 = Product.createNew("id1", "A", Category.BOOKS, 5, t1);
        Product product2 = Product.createNew("id2", "B", Category.TOYS,  8, t2);

        warehouse.addProduct(product1);
        warehouse.addProduct(product2);

        var result = warehouse.getProductsByCategorySorted(Category.ELECTRONICS);

        assertTrue(result.isEmpty());
    }


    @Test
    void getProductsByCategorySorted_nullCategory_throwsNullPointerException() {
        assertThrows(NullPointerException.class,
                () -> warehouse.getProductsByCategorySorted(null));
    }
}
