package com.jan_elia.warehouse.service;

import com.jan_elia.warehouse.entities.Category;
import com.jan_elia.warehouse.entities.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class WarehouseTest {

    private final Clock fixedClock = Clock.fixed(
            LocalDate.of(2025, 1, 2).atStartOfDay(ZoneId.systemDefault()).toInstant(),
            ZoneId.systemDefault()
    );
    private Warehouse warehouse;

    @BeforeEach
    void setUp() {
        warehouse = new Warehouse(fixedClock);
    }

    private Product makeProduct(String id, String name, Category category, int rating, LocalDate created, LocalDate modified) {
        return new Product(id, name, category, rating, created, modified);
    }

    // ---- addProduct ----
    @Test
    void addProduct_success() {
        Product p = makeProduct("1", "Apple", Category.FOOD, 5,
                LocalDate.of(2025, 1, 1), LocalDate.of(2025, 1, 1));
        warehouse.addProduct(p);
        assertEquals(1, warehouse.getAllProducts().size());
    }

    @Test
    void addProduct_failure_nullProduct() {
        assertThrows(IllegalArgumentException.class, () -> warehouse.addProduct(null));
    }

    @Test
    void addProduct_failure_blankName() {
        Product p = makeProduct("2", " ", Category.FOOD, 5,
                LocalDate.of(2025, 1, 1), LocalDate.of(2025, 1, 1));
        assertThrows(IllegalArgumentException.class, () -> warehouse.addProduct(p));
    }

    @Test
    void addProduct_failure_nullCategory() {
        Product p = makeProduct("3", "Banana", null, 5,
                LocalDate.of(2025, 1, 1), LocalDate.of(2025, 1, 1));
        assertThrows(IllegalArgumentException.class, () -> warehouse.addProduct(p));
    }

    @Test
    void addProduct_failure_invalidRating() {
        Product p = makeProduct("4", "Orange", Category.FOOD, 11,
                LocalDate.of(2025, 1, 1), LocalDate.of(2025, 1, 1));
        assertThrows(IllegalArgumentException.class, () -> warehouse.addProduct(p));
    }

    @Test
    void addProduct_failure_duplicateId() {
        Product p1 = makeProduct("5", "Apple", Category.FOOD, 5,
                LocalDate.of(2025, 1, 1), LocalDate.of(2025, 1, 1));
        Product p2 = makeProduct("5", "Banana", Category.FOOD, 5,
                LocalDate.of(2025, 1, 1), LocalDate.of(2025, 1, 1));
        warehouse.addProduct(p1);
        assertThrows(IllegalArgumentException.class, () -> warehouse.addProduct(p2));
    }
    // ---- getProductById ----
    @Test
    void getProductById_success() {
        Product p = makeProduct("10", "Milk", Category.FOOD, 5,
                LocalDate.of(2025, 1, 1), LocalDate.of(2025, 1, 1));
        warehouse.addProduct(p);
        assertEquals(p, warehouse.getProductById("10"));
    }

    @Test
    void getProductById_failure_nullId() {
        assertThrows(IllegalArgumentException.class, () -> warehouse.getProductById(null));
    }

    @Test
    void getProductById_failure_blankId() {
        assertThrows(IllegalArgumentException.class, () -> warehouse.getProductById(" "));
    }

    @Test
    void getProductById_failure_notFound() {
        assertThrows(java.util.NoSuchElementException.class, () -> warehouse.getProductById("999"));
    }

    // ---- getAllProducts ----
    @Test
    void getAllProducts_success() {
        Product p1 = makeProduct("20", "A", Category.FOOD, 5,
                LocalDate.of(2025, 1, 1), LocalDate.of(2025, 1, 1));
        Product p2 = makeProduct("21", "B", Category.FOOD, 5,
                LocalDate.of(2025, 1, 1), LocalDate.of(2025, 1, 1));
        warehouse.addProduct(p1);
        warehouse.addProduct(p2);
        List<Product> list = warehouse.getAllProducts();
        assertEquals(2, list.size());
    }

    @Test
    void getAllProducts_defensive() {
        Product p1 = makeProduct("22", "C", Category.FOOD, 5,
                LocalDate.of(2025, 1, 1), LocalDate.of(2025, 1, 1));
        warehouse.addProduct(p1);
        List<Product> list = warehouse.getAllProducts();
        assertThrows(UnsupportedOperationException.class, () -> list.add(p1));
    }

    // ---- getProductsByCategorySorted ----
    @Test
    void getProductsByCategorySorted_success_sorted() {
        Product p1 = makeProduct("30", "Zebra", Category.TOYS, 5,
                LocalDate.of(2025, 1, 1), LocalDate.of(2025, 1, 1));
        Product p2 = makeProduct("31", "apple", Category.TOYS, 5,
                LocalDate.of(2025, 1, 1), LocalDate.of(2025, 1, 1));
        warehouse.addProduct(p1);
        warehouse.addProduct(p2);
        List<Product> list = warehouse.getProductsByCategorySorted(Category.TOYS);
        assertEquals("apple", list.get(0).getName());
        assertEquals("Zebra", list.get(1).getName());
    }

    @Test
    void getProductsByCategorySorted_emptyList() {
        List<Product> list = warehouse.getProductsByCategorySorted(Category.ELECTRONICS);
        assertTrue(list.isEmpty());
    }

    @Test
    void getProductsByCategorySorted_nullCategory() {
        assertThrows(IllegalArgumentException.class, () -> warehouse.getProductsByCategorySorted(null));
    }
}

// Test plan for Warehouse

// updateProduct
// TODO: success: fields updated; createdDate unchanged; modifiedDate = now(clock)
// TODO: failure: id not found -> NoSuchElementException
// TODO: failure: name blank -> IllegalArgumentException
// TODO: failure: category null -> IllegalArgumentException
// TODO: failure: rating out of range -> IllegalArgumentException
// TODO: failure: id null/blank -> IllegalArgumentException
//
// getProductsCreatedAfter
// TODO: success: returns only products strictly after date
// TODO: success: product on the same date is excluded
// TODO: failure: date is null -> IllegalArgumentException
//
// getModifiedProducts
// TODO: success: returns only products where modifiedDate != createdDate
// TODO: success: returns empty list when none are modified
