package org.example.entities;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductTest {

    @Test
    @DisplayName("Product constructor: sets unique ID")
    public void canCreateProductWithId() {
        Product product = new Product("1");
        assertThat(product.getId()).isEqualTo("1");
    }

    @Test
    @DisplayName("Product constructor: sets name")
    public void canCreateProductWithName() {
        Product product = new Product("1", "Laptop");
        assertThat(product.getName()).isEqualTo("Laptop");
    }

    @Test
    @DisplayName("Product constructor: sets category")
    public void canCreateProductWithCategory() {
        Product product = new Product("1", "Laptop", Category.GENERAL);
        assertThat(product.getCategory()).isEqualTo(Category.GENERAL);
    }

    @Test
    @DisplayName("Product constructor: sets rating")
    public void canCreateProductWithRating() {
        Product product = new Product("1", "Laptop", Category.GENERAL, 1);
        assertThat(product.getRating()).isEqualTo(1);
    }

    @Test
    @DisplayName("Product constructor: sets createdDate")
    public void canCreateProductWithGetCreatedDate() {
        try (MockedStatic mockedStatic = Mockito.mockStatic(LocalDateTime.class, Mockito.CALLS_REAL_METHODS)) {
            LocalDateTime currentDate = LocalDateTime.of(2025, 1, 1, 18, 0, 0, 0);
            mockedStatic.when(LocalDateTime::now).thenReturn(currentDate);

            Product product = new Product("1", "Laptop", Category.GENERAL, 1);
            assertThat(product.getCreatedDate()).isEqualTo(currentDate);
        }
    }

    @Test
    @DisplayName("update(): updates modifiedDate and returns new Product")
    public void canCreateProductWithModifiedDate() {
        Product product;
        Product updated;

        try (MockedStatic mockedStatic = Mockito.mockStatic(LocalDateTime.class, Mockito.CALLS_REAL_METHODS)) {
            LocalDateTime currentDate = LocalDateTime.of(2025, 1, 1, 18, 0, 0, 0);
            mockedStatic.when(LocalDateTime::now).thenReturn(currentDate);
            product = new Product("1", "Laptop", Category.GENERAL, 1);
        }

        try (MockedStatic mockedStatic = Mockito.mockStatic(LocalDateTime.class, Mockito.CALLS_REAL_METHODS)) {
            LocalDateTime updateDate = LocalDateTime.of(2025, 1, 5, 18, 0, 0, 0);
            mockedStatic.when(LocalDateTime::now).thenReturn(updateDate);
            updated = product.update("Notebook", Category.GENERAL, 2);

            assertThat(updated.getModifiedDate()).isEqualTo(updateDate);
            assertThat(product.getModifiedDate()).isNotEqualTo(updateDate);
        }
    }

}
