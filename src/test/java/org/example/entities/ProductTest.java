package org.example.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductTest {

    @Test
    void shouldCreateProductWithId() {
        // Given
        String id = "1";
        String name = "Test product";
        // When
        Product product = new Product(id, name);
        // Then
        assertEquals(id, product.id());

    }

    @Test
    void shouldCreateProductWithIdAndName() {
        String id = "1";
        String name = "Test product";

        Product product = new Product(id, name);

        assertEquals(id, product.id());
        assertEquals(name, product.name());
    }
}
