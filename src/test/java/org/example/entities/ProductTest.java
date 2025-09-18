package org.example.entities;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;

public class ProductTest {

    @Test
    void creatingProduct_withValidData_setsAllFields() {
        LocalDate today = LocalDate.now();

        Product product = Product.builder()
                .id("p-001")
                .name("Samsung 55\" 4K")
                .category(Category.TV)
                .rating(8)
                .createdDate(today)
                .modifiedDate(today)
                .build();

        // Kontrollerar att produktens fält är satta korrekt
        assertThat(product.id()).isEqualTo("p-001");
        assertThat(product.name()).isEqualTo("Samsung 55\" 4K");
        assertThat(product.category()).isEqualTo(Category.TV);
        assertThat(product.rating()).isEqualTo(8);
        assertThat(product.createdDate()).isEqualTo(today);
        assertThat(product.modifiedDate()).isEqualTo(today);
    }

    @Test
    void creatingProduct_withBlankName_throwsException() {
        LocalDate today = LocalDate.now();

        // Kontrollerar att ett exception kastas när namnet är blank
        assertThatThrownBy(() ->
                Product.builder()
                    .id("p-001")
                    .name("   ")
                    .category(Category.TV)
                    .rating(8)
                    .createdDate(today)
                    .modifiedDate(today)
                    .build()
                )
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("name");
    }

    @Test
    void creatingProduct_withRatingOutside0to10_throwsException() {
        LocalDate today = LocalDate.now();

        // rating < 0
        assertThatThrownBy(() ->
                Product.builder()
                    .id("p-002")
                    .name("Budget Laptop")
                    .category(Category.COMPUTER)
                    .rating(-1)
                    .createdDate(today)
                    .modifiedDate(today)
                    .build()
                )
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("rating");

        // rating > 10
        assertThatThrownBy(() ->
                Product.builder()
                    .id("p-003")
                    .name("Pro Laptop")
                    .category(Category.COMPUTER)
                    .rating(11)
                    .createdDate(today)
                    .modifiedDate(today)
                    .build()
                )
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("rating");
    }

    @Test
    void creatingProduct_withModifiedBeforeCreated_throwsException() {
        LocalDate created = LocalDate.now();
        LocalDate modified = created.minusDays(1);

        assertThatThrownBy(() ->
                Product.builder()
                    .id("p-004")
                    .name("Budget Laptop")
                    .category(Category.COMPUTER)
                    .rating(8)
                    .createdDate(created)
                    .modifiedDate(modified)
                    .build()
                )
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("modified");
    }

    @Test
    void creatingProduct_withNullArguments_throwsException() {
        LocalDate today = LocalDate.now();

        // id == null
        assertThatThrownBy(() ->
                Product.builder()
                    .name("Budget Laptop")
                    .category(Category.COMPUTER)
                    .rating(8)
                    .createdDate(today)
                    .modifiedDate(today)
                    .build()
                )
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("id");

        // name == null
        assertThatThrownBy(() ->
                Product.builder()
                    .id("p-002")
                    .category(Category.COMPUTER)
                    .rating(8)
                    .createdDate(today)
                    .modifiedDate(today)
                    .build()
                )
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("name");

        // category == null
        assertThatThrownBy(() ->
                Product.builder()
                    .id("p-002")
                    .name("Budget Laptop")
                    .rating(8)
                    .createdDate(today)
                    .modifiedDate(today)
                    .build()
                )
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("category");
    }
}
