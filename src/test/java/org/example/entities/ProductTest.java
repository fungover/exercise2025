package org.example.entities;


import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    @Test
    void createProduct() {
        LocalDateTime now = LocalDateTime.now();

        Product product = new Product(
                "1",
                "Harry Potter and the goblet of fire",
                Category.BOOKS,
                9,
                now,
                now
        );

        assertEquals("1", product.id());
        assertEquals("Harry Potter and the goblet of fire", product.name());
        assertEquals(Category.BOOKS, product.category());
        assertEquals(9, product.rating());
        assertEquals(now, product.createdDate());
        assertEquals(now, product.modifiedDate());
    }

}