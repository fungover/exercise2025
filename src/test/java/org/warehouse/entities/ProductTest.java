package org.warehouse.entities;

import org.junit.jupiter.api.Test;
import org.warehouse.Warehouse;

import java.time.LocalDate;

public class ProductTest {
  @Test
  public void canCreateProductWithSpecifiedAttributes() {
    Warehouse product = new Product(
            "1",
            "Jacket",
            Category.CLOTHING,
            10,
            LocalDate.now(),
            LocalDate.now());
  }
}
