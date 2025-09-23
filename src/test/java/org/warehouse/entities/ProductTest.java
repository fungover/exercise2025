package org.warehouse.entities;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductTest {
  @Test
  public void canCreateProductWithSpecifiedAttributes() {
    Product product = new Product(
            "1",
            "Film",
            Category.THRILLER,
            10,
            LocalDate.now(),
            LocalDate.now());

    assertEquals("1", product.id());
    assertEquals("Film", product.name());
    assertEquals(Category.THRILLER, product.category());
  }
}
