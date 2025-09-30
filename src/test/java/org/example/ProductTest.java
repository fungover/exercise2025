package org.example;

import org.fungover.entities.Category;
import org.fungover.entities.Product;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Instant;


public class ProductTest {

    @Test
    void createProduct_success() {
        Product p = new Product.Builder().name("Laptop").category(Category.ELECTRONICS).rating(8).build();

        assertNotNull(p.identifier());
        assertEquals("Laptop", p.name());
        assertEquals(Category.ELECTRONICS, p.category());
        assertEquals(8, p.rating());
        assertNotNull(p.createdDate());
        assertNotNull(p.lastModifiedDate());
        assertTrue(!p.createdDate().isAfter(p.lastModifiedDate()));
    }

    @Test
    void createProduct_blankName_throws() {
        assertThrows(IllegalArgumentException.class, () -> new Product.Builder().name("   ").category(Category.OTHER).rating(5).build());
    }

    @Test
    void createProduct_invalidRating_throws() {
        assertThrows(IllegalArgumentException.class, () -> new Product.Builder().name("Phone").category(Category.ELECTRONICS).rating(-1).build());
        assertThrows(IllegalArgumentException.class, () -> new Product.Builder().name("Phone").category(Category.ELECTRONICS).rating(11).build());
    }

    @Test
    void updateFields_preservesIdAndCreated_updatesOthers() throws InterruptedException {
        Product original = new Product.Builder().name("Chair").category(Category.FURNITURE).rating(6).build();

        String originalId = original.identifier();
        Instant originalCreated = original.createdDate();

        Thread.sleep(2);

        Product updated = original.updateFields(originalId, "Armchair", Category.FURNITURE, 9);

        assertEquals(originalId, updated.identifier());
        assertEquals(originalCreated, updated.createdDate());
        assertEquals("Armchair", updated.name());
        assertEquals(9, updated.rating());
        assertTrue(!updated.lastModifiedDate().isBefore(original.lastModifiedDate()));
    }
}