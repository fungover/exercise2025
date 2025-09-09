package org.example.entities;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;

public class ProductTest {

    @Test
    void creatingProduct_withValidData_setsAllFields() {
        // Skapar testdata
        String id = "p-001";
        String name = "Samsung 55\" 4K";
        Category category = Category.TV;
        int rating = 8;
        LocalDate created = LocalDate.now();

        // Skapar produkt
        Product product = new Product(id, name, category, rating, created, created);

        // Kontrollerar att produktens fält är satta korrekt
        assertThat(product.getId()).isEqualTo(id);
        assertThat(product.getName()).isEqualTo(name);
        assertThat(product.getCategory()).isEqualTo(category);
        assertThat(product.getRating()).isEqualTo(rating);
        assertThat(product.getCreatedDate()).isEqualTo(created);
        assertThat(product.getModifiedDate()).isEqualTo(created);
    }

    @Test
    void creatingProduct_withBlankName_throwsException() {
        String id = "p-001";
        String blankName = "   ";
        LocalDate today = LocalDate.now();

        // Kontrollerar att ett exception kastas när namnet är blank
        assertThatThrownBy(() ->
                new Product(id, blankName, Category.TV, 8, today, today))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("name");
    }

    @Test
    void creatingProduct_withRatingOutside0to10_throwsException() {
        LocalDate today = LocalDate.now();

        // rating < 0
        assertThatThrownBy(() ->
                new Product("p-002", "Budget Laptop", Category.COMPUTER, -1, today, today))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("rating");

        // rating > 10
        assertThatThrownBy(() ->
                new Product("p-003", "Pro Laptop", Category.COMPUTER, 11, today, today)
        )
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("rating");
    }

    @Test
    void creatingProduct_withModifiedBeforeCreated_throwsException() {
        LocalDate created = LocalDate.now();
        LocalDate modified = created.minusDays(1);

        assertThatThrownBy(() ->
                new Product("p-002", "Budget Laptop", Category.COMPUTER, 8, created, modified))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("modified");
    }

    @Test
    void creatingProduct_withNullArguments_throwsException() {
        LocalDate today = LocalDate.now();

        // id == null
        assertThatThrownBy(() ->
                new Product(null, "Budget Laptop", Category.COMPUTER, 8, today, today))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("id");

        // name == null
        assertThatThrownBy(() ->
                new Product("p-002", null, Category.COMPUTER, 8, today, today))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("name");

        // category == null
        assertThatThrownBy(() ->
                new Product("p-002", "Budget Laptop", null, 8, today, today))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("category");

        // createdDate == null
        assertThatThrownBy(() ->
                new Product("p-002", "Budget Laptop", Category.COMPUTER, 8, null, today))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("created");

        // modifiedDate == null
        assertThatThrownBy(() ->
                new Product("p-002", "Budget Laptop", Category.COMPUTER, 8, today, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("modified");
    }
}
