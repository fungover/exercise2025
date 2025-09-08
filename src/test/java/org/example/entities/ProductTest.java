package org.example.entities;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    @Test
    void testConstructorAndGetters() {
        LocalDate created = LocalDate.of(2025, 9, 8);
        LocalDate modified = LocalDate.of(2025, 9, 9);
        Product product = new Product("Laptop", 8, created, modified, Category.ELECTRONICS);

        assertNotNull(product.getId(), "ID should not be null");
        assertEquals("Laptop", product.getName());
        assertEquals(8, product.getRating());
        assertEquals(created, product.getCreatedDate());
        assertEquals(modified, product.getModifiedDate());
        assertEquals(Category.ELECTRONICS, product.getCategory());
    }

    @Test
    void testInvalidNameThrowsException() {
        LocalDate today = LocalDate.now();
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new Product("", 5, today, today, Category.FOOD));
        assertEquals("Name cannot be empty", exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class, () ->
                new Product(null, 5, today, today, Category.FOOD));
        assertEquals("Name cannot be empty", exception.getMessage());
    }

    @Test
    void testInvalidRatingThrowsException() {
        LocalDate today = LocalDate.now();
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new Product("Apple", -1, today, today, Category.FOOD));
        assertEquals("Rating must be between 0 and 10", exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class, () ->
                new Product("Apple", 11, today, today, Category.FOOD));
        assertEquals("Rating must be between 0 and 10", exception.getMessage());
    }

    @Test
    void testWithUpdatesCreatesNewProduct() {
        LocalDate created = LocalDate.of(2025, 9, 8);
        LocalDate modified = LocalDate.of(2025, 9, 9);
        Product product = new Product("Laptop", 8, created, modified, Category.ELECTRONICS);

        Product updated = product.withUpdates("Gaming Laptop", Category.ELECTRONICS, 9);

        assertEquals("Laptop", product.getName());
        assertEquals(8, product.getRating());
        assertEquals(modified, product.getModifiedDate());

        assertEquals("Gaming Laptop", updated.getName());
        assertEquals(9, updated.getRating());
        assertEquals(product.getCreatedDate(), updated.getCreatedDate());
        assertNotEquals(product.getModifiedDate(), updated.getModifiedDate());
    }
}