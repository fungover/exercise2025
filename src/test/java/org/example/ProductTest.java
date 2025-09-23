package org.example;

import org.fungover.entities.Category;
import org.fungover.entities.Product;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Instant;


public class ProductTest {

    @Test
    void createProduct_success() {
        Product p = new Product("Laptop", Category.ELECTRONICS, 8);

        assertNotNull(p.identifier());
        assertEquals("Laptop", p.name());
        assertEquals(Category.ELECTRONICS, p.category());
        assertEquals(8, p.rating());
        assertNotNull(p.createdDate());
        assertNotNull(p.lastModifiedDate());
        assertTrue(!p.createdDate().isAfter(p.lastModifiedDate()));
    }

    @Test
    void createProduct_blankName_usesFallbackString() {
        Product p = new Product("   ", Category.OTHER, 5);
        assertEquals("Name cannot be empty", p.name());
    }

    @Test
    void createProduct_invalidRating_throws() {
        assertThrows(IllegalArgumentException.class, () -> new Product("Phone", Category.ELECTRONICS, -1));
        assertThrows(IllegalArgumentException.class, () -> new Product("Phone", Category.ELECTRONICS, 11));
    }

    @Test
    void updateFields_preservesIdAndCreated_updatesOthers() throws InterruptedException {
        Product original = new Product("Chair", Category.FURNITURE, 6);
        String originalId = original.identifier();
        Instant originalCreated = original.createdDate();

        Thread.sleep(2);

        Product updated = original.updateFields(originalId, "Armchair", Category.FURNITURE, 9);

        assertEquals(originalId, updated.identifier());
        assertEquals(originalCreated, updated.createdDate());
        assertEquals("Armchair", updated.name());
        assertEquals(9, updated.rating());
        assertTrue(updated.lastModifiedDate().isAfter(original.lastModifiedDate()) || updated.lastModifiedDate().equals(original.lastModifiedDate()));
    }
}