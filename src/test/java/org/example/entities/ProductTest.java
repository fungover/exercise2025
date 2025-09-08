package org.example.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductTest {

    private String id;
    private String name;
    private Category category;
    private int rating;
    private LocalDate createdDate;
    private LocalDate modifiedDate;
    private Product product;

    @BeforeEach
    void setUp() {
        id = "1";
        name = "Test product";
        category = Category.ELECTRONICS;
        rating = 8;
        createdDate = LocalDate.of(2025, 9, 8);
        modifiedDate = createdDate;

        product = new Product(id, name, category, rating, createdDate);
    }

    @Test
    void shouldCreateProductWithId() {
        assertEquals(id, product.id());
    }

    @Test
    void shouldCreateProductWithIdAndName() {
        assertEquals(id, product.id());
        assertEquals(name, product.name());
    }

    @Test
    void productShouldHaveCategory() {
        assertEquals(category, product.category());
    }

    @Test
    void productShouldHaveRating() {
        assertEquals(rating, product.rating());
    }

    // Using this test for Given, When, Then reminder
    @Test
    void shouldHaveProductWithAllFields() {
        // Given
        String id = "1";
        String name = "Test product";
        Category category = Category.ELECTRONICS;
        int rating = 8;
        LocalDate createdDate = LocalDate.of(2025, 9, 8);
        modifiedDate = createdDate;
        // When
        Product product = new Product(id, name, category, rating, createdDate);
        // Then
        assertEquals(id, product.id());
        assertEquals(name, product.name());
        assertEquals(category, product.category());
        assertEquals(rating, product.rating());
        assertEquals(createdDate, product.createdDate());
    }

    @Test
    void productShouldHaveCreatedDate() {
        assertEquals(createdDate, product.createdDate());
    }

    @Test
    void modifiedDateShouldInitiallyEqualCreatedDate() {
        assertEquals(createdDate, product.createdDate());
        assertEquals(modifiedDate, product.modifiedDate());
        assertEquals(product.createdDate(), product.modifiedDate());
    }
}
