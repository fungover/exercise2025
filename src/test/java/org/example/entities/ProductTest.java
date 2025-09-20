package org.example.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class ProductTest {

    private Product baseProduct;
    private LocalDate testDate;

    @BeforeEach
    void setUp() {
        testDate = LocalDate.of(2025, 9, 20);
        baseProduct = new Product(
            "1",
            "iPhone 15",
            Category.ELECTRONICS,
            9,
            testDate,
            testDate
        );
    }

    @Test
    void withUpdatedFields_Success() {
        // When
        Product updated = baseProduct.withUpdatedFields("iPad Pro", Category.ELECTRONICS, 10, testDate.plusDays(1));

        // Then
        assertEquals("1", updated.id());
        assertEquals("iPad Pro", updated.name());
        assertEquals(Category.ELECTRONICS, updated.category());
        assertEquals(10, updated.rating());
        assertEquals(testDate, updated.createdDate()); // Should remain unchanged
        assertEquals(testDate.plusDays(1), updated.modifiedDate()); // Should be updated
    }

    @Test
    void withUpdatedFields_NullName_ThrowsException() {
        // When & Then
        assertThrows(NullPointerException.class, () -> {
            baseProduct.withUpdatedFields(null, Category.ELECTRONICS, 8, testDate);
        });
    }

    @Test
    void withUpdatedFields_EmptyName_ThrowsException() {
        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            baseProduct.withUpdatedFields("   ", Category.ELECTRONICS, 8, testDate);
        });
        assertTrue(exception.getMessage().contains("Name cannot be empty"));
    }

    @Test
    void withUpdatedFields_NullCategory_ThrowsException() {
        // When & Then
        assertThrows(NullPointerException.class, () -> {
            baseProduct.withUpdatedFields("iPad", null, 8, testDate);
        });
    }

    @Test
    void withUpdatedFields_InvalidRatingTooLow_ThrowsException() {
        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            baseProduct.withUpdatedFields("iPad", Category.ELECTRONICS, -1, testDate);
        });
        assertTrue(exception.getMessage().contains("Rating must be between 0 and 10"));
    }

    @Test
    void withUpdatedFields_InvalidRatingTooHigh_ThrowsException() {
        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            baseProduct.withUpdatedFields("iPad", Category.ELECTRONICS, 11, testDate);
        });
        assertTrue(exception.getMessage().contains("Rating must be between 0 and 10"));
    }

    @Test
    void withUpdatedFields_NullModifiedDate_ThrowsException() {
        // When & Then
        assertThrows(NullPointerException.class, () -> {
            baseProduct.withUpdatedFields("iPad", Category.ELECTRONICS, 8, null);
        });
    }

    @Test
    void withUpdatedFields_TrimsName() {
        // When
        Product updated = baseProduct.withUpdatedFields("  iPad Pro  ", Category.ELECTRONICS, 10, testDate);

        // Then
        assertEquals("iPad Pro", updated.name()); // Should be trimmed
    }

    @Test
    void withUpdatedFields_ConvenienceMethod_UsesCurrentDate() {
        // Given
        LocalDate beforeCall = LocalDate.now();

        // When
        Product updated = baseProduct.withUpdatedFields("iPad Pro", Category.ELECTRONICS, 10);

        // Then
        assertEquals("iPad Pro", updated.name());
        assertEquals(Category.ELECTRONICS, updated.category());
        assertEquals(10, updated.rating());
        assertEquals(testDate, updated.createdDate()); // Should remain unchanged

        // Modified date should be today (or after our before timestamp)
        assertTrue(!updated.modifiedDate().isBefore(beforeCall));
    }

    @Test
    void withUpdatedFields_ValidRatingBoundaries() {
        // Test minimum valid rating
        Product minRating = baseProduct.withUpdatedFields("Test", Category.ELECTRONICS, 0, testDate);
        assertEquals(0, minRating.rating());

        // Test maximum valid rating
        Product maxRating = baseProduct.withUpdatedFields("Test", Category.ELECTRONICS, 10, testDate);
        assertEquals(10, maxRating.rating());
    }
}