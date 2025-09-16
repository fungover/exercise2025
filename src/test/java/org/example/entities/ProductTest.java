package org.example.entities;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

/** Field-rule checks for "Product" */
class ProductTest {

    /** builder: when dates are omitted, createdDate == modifiedDate (defaults) */
    @Test
    void builder_setsCreatedEqualsModified_whenDatesOmitted() {
        Product product = Product.builder()
                .id("id1")
                .name("Name")
                .category(Category.BOOKS)
                .rating(7)
                .build();

        assertNotNull(product.createdDate());
        assertNotNull(product.modifiedDate());
        assertEquals(product.createdDate(), product.modifiedDate());
    }

    /** builder: null id -> IllegalArgumentException("id required") */
    @Test
    void builder_nullId_throwsWithMessage() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> Product.builder()
                        .id(null)
                        .name("Name")
                        .category(Category.BOOKS)
                        .rating(7)
                        .build());
        assertEquals("id required", ex.getMessage());
    }

    /** builder: blank name -> IllegalArgumentException("name required") */
    @Test
    void builder_blankName_throwsWithMessage() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> Product.builder()
                        .id("id1")
                        .name("   ")
                        .category(Category.BOOKS)
                        .rating(7)
                        .build());
        assertEquals("name required", ex.getMessage());
    }

    /** builder: null category -> IllegalArgumentException("category required") */
    @Test
    void builder_nullCategory_throwsWithMessage() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> Product.builder()
                        .id("id1")
                        .name("Name")
                        .category(null)
                        .rating(7)
                        .build());
        assertEquals("category required", ex.getMessage());
    }

    /** builder: rating out of range -> IllegalArgumentException("rating 0–10") */
    @Test
    void builder_invalidRating_throwsWithMessage() {
        IllegalArgumentException exLow = assertThrows(IllegalArgumentException.class,
                () -> Product.builder()
                        .id("id1")
                        .name("Name")
                        .category(Category.BOOKS)
                        .rating(-1)
                        .build());
        assertEquals("rating 0–10", exLow.getMessage());

        IllegalArgumentException exHigh = assertThrows(IllegalArgumentException.class,
                () -> Product.builder()
                        .id("id1")
                        .name("Name")
                        .category(Category.BOOKS)
                        .rating(11)
                        .build());
        assertEquals("rating 0–10", exHigh.getMessage());
    }

    /** builder: explicit dates are respected (createdDate != modifiedDate when provided) */
    @Test
    void builder_explicitDates_areRespected() {
        LocalDateTime createdDate = LocalDateTime.of(2025, 9, 1, 12, 0);
        LocalDateTime modifiedDate = createdDate.plusHours(2);

        Product product = Product.builder()
                .id("id1")
                .name("Name")
                .category(Category.BOOKS)
                .rating(7)
                .createdDate(createdDate)
                .modifiedDate(modifiedDate)
                .build();

        assertEquals(createdDate, product.createdDate());
        assertEquals(modifiedDate, product.modifiedDate());
    }
}
