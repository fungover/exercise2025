package org.example.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class ProductTest {

    private String id;
    private String name;
    private Category category;
    private int rating;
    private LocalDate createdDate;
    private LocalDate modifiedDate;
    private Product product;
    private BigDecimal price;

    @BeforeEach
    void setUp() {
        id = "1";
        name = "Test product";
        category = Category.ELECTRONICS;
        rating = 8;
        createdDate = LocalDate.of(2025, 9, 8);
        modifiedDate = createdDate;
        price = BigDecimal.valueOf(99.99);

        product = Product.builder()
                .id(id)
                .name(name)
                .category(category)
                .rating(rating)
                .createdDate(createdDate)
                .price(price)
                .build();
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
        product = Product.builder()
                .id(id)
                .name(name)
                .category(category)
                .rating(rating)
                .createdDate(createdDate)
                .price(BigDecimal.valueOf(999.99))
                .build();
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

    @Test
    void shouldThrowExceptionForInvalidRatingInFiveParamConstructor() {
        assertThrows(IllegalArgumentException.class, () ->
                Product.builder()
                        .id("1")
                        .name("Test")
                        .category(Category.ELECTRONICS)
                        .rating(11)
                        .createdDate(LocalDate.now())
                        .build());
    }

    @Test
    void shouldThrowExceptionForInvalidRatingInSixParamConstructor() {
        assertThrows(IllegalArgumentException.class, () ->
                Product.builder()
                        .id("1")
                        .name("Test")
                        .category(Category.ELECTRONICS)
                        .rating(-1)
                        .createdDate(LocalDate.now())
                        .modifiedDate(LocalDate.now())
                        .build());
    }

    @Test
    void shouldCreateProductUsingBuilder() {
        Product product = Product.builder()
                .id("2")
                .name("Test Product 2")
                .category(Category.ELECTRONICS)
                .rating(8)
                .createdDate(LocalDate.of(2025, 9, 25))
                .price(BigDecimal.valueOf(100.00))
                .build();

        assertEquals("2", product.id());
        assertEquals("Test Product 2", product.name());
        assertEquals(Category.ELECTRONICS, product.category());
        assertEquals(8, product.rating());
    }

    @Test
    void shouldThrowExceptionForMissingIdInBuilder() {
        assertThrows(IllegalArgumentException.class, () ->
                Product.builder()
                        .name("Test")
                        .category(Category.ELECTRONICS)
                        .rating(8)
                        .createdDate(LocalDate.now())
                        .build());
    }

    @Test
    void shouldThrowExceptionForMissingNameInBuilder() {
        assertThrows(IllegalArgumentException.class, () ->
                Product.builder()
                        .id("1")
                        .category(Category.ELECTRONICS)
                        .rating(8)
                        .createdDate(LocalDate.now())
                        .build());
    }

    @Test
    void shouldThrowExceptionForMissingCategoryInBuilder() {
        assertThrows(IllegalArgumentException.class, () ->
                Product.builder()
                        .id("1")
                        .name("Test")
                        .rating(8)
                        .createdDate(LocalDate.now())
                        .build());
    }

    @Test
    void shouldThrowExceptionForInvalidRatingInBuilder() {
        assertThrows(IllegalArgumentException.class, () ->
                Product.builder()
                        .id("1")
                        .name("Test")
                        .category(Category.ELECTRONICS)
                        .rating(11)
                        .createdDate(LocalDate.now())
                        .build());
    }

    @Test
    void shouldSetDefaultDatesWhenNotProvided() {
        Product product = Product.builder()
                .id("1")
                .name("Test")
                .category(Category.SPORTS)
                .rating(10)
                .price(BigDecimal.valueOf(49.00))
                .build();

        assertNotNull(product.createdDate());
        assertEquals(product.createdDate(), product.modifiedDate());
    }

    @Test
    void shouldThrowExceptionForMissingPriceInBuilder() {
        assertThrows(IllegalArgumentException.class, () ->
                Product.builder()
                        .id("1")
                        .name("Test")
                        .category(Category.ELECTRONICS)
                        .rating(8)
                        .createdDate(LocalDate.now())
                        .build());
    }

    @Test
    void shouldHaveTheCorrectPrice() {
        Product product = Product.builder()
                .id("1")
                .name("Test")
                .category(Category.SPORTS)
                .rating(10)
                .price(BigDecimal.valueOf(49.00))
                .build();

        assertEquals(BigDecimal.valueOf(49.00), product.price());
        assertNotNull(product.price());
    }
}
