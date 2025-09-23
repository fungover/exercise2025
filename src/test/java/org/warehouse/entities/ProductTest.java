package org.warehouse.entities;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductTest {
  @Test
  public void canCreateProductWithSpecifiedAttributes() {
    Product product = new Product.Builder()
            .setId("1")
            .setName("Film")
            .setCategory(Category.THRILLER)
            .setRating(10)
            .setCreatedDate(LocalDate.now())
            .setModifiedDate(null)
            .build();

    assertEquals("1", product.id());
    assertEquals("Film", product.name());
    assertEquals(Category.THRILLER, product.category());
  }
}
