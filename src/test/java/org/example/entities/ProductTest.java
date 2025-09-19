package org.example.entities;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

/** Field-rule checks for "Product" */
class ProductTest {

    /** createNew: sets createdDate == modifiedDate */
    @Test
    void createNew_setsCreatedEqualsModified() {
        LocalDateTime creationTime = LocalDateTime.of(2025, 9, 1, 12, 0);

        Product product = Product.createNew("id1", "Name", Category.BOOKS, 7, creationTime);

        assertEquals(creationTime, product.createdDate());
        assertEquals(creationTime, product.modifiedDate());
    }

    /** createNew: null id -> IllegalArgumentException("id required") */
    @Test
    void createNew_nullId_throwsWithMessage() {
        LocalDateTime creationTime = LocalDateTime.of(2025, 9, 1, 12, 0);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> Product.createNew(null, "Name", Category.BOOKS, 7, creationTime));
        assertEquals("id required", ex.getMessage());
    }

    /** createNew: blank name -> IllegalArgumentException("name required") */
    @Test
    void createNew_blankName_throwsWithMessage() {
        LocalDateTime creationTime = LocalDateTime.of(2025, 9, 1, 12, 0);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> Product.createNew("id1", "   ", Category.BOOKS, 7, creationTime));
        assertEquals("name required", ex.getMessage());
    }

    /** createNew: null category -> IllegalArgumentException("category required") */
    @Test
    void createNew_nullCategory_throwsWithMessage() {
        LocalDateTime creationTime = LocalDateTime.of(2025, 9, 1, 12, 0);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> Product.createNew("id1", "Name", null, 7, creationTime));
        assertEquals("category required", ex.getMessage());
    }

    /** createNew: rating out of range -> IllegalArgumentException("rating 0–10") */
    @Test
    void createNew_invalidRating_throwsWithMessage() {
        LocalDateTime creationTime = LocalDateTime.of(2025, 9, 1, 12, 0);

        IllegalArgumentException exLow = assertThrows(IllegalArgumentException.class,
                () -> Product.createNew("id1", "Name", Category.BOOKS, -1, creationTime));
        assertEquals("rating 0–10", exLow.getMessage());

        IllegalArgumentException exHigh = assertThrows(IllegalArgumentException.class,
                () -> Product.createNew("id1", "Name", Category.BOOKS, 11, creationTime));
        assertEquals("rating 0–10", exHigh.getMessage());
    }

    /** createNew: null 'now' -> NullPointerException("createdDate") (constructor checks createdDate first) */
    @Test
    void createNew_nullNow_throwsWithMessage() {
        NullPointerException ex = assertThrows(NullPointerException.class,
                () -> Product.createNew("id1", "Name", Category.BOOKS, 7, null));
        assertEquals("createdDate", ex.getMessage());
    }
}
