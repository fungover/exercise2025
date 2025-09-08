package org.example.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductTest {

    @Test
    void shouldCreateProductWithId() {
        // Given
        String id = "1";
        // When
        Product product = new Product(id);
        // Then
        assertEquals(id, product.id());

    }
}
