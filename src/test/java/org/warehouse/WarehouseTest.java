package org.warehouse;

import org.junit.jupiter.api.Test;
import org.warehouse.entities.Category;
import org.warehouse.entities.Product;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class WarehouseTest {

  @Test
  public void canCreateWarehouseAndGetName() {
    Warehouse warehouse = new Warehouse("Test warehouse");

    assertEquals(warehouse.getName(), "Test warehouse");
  }

  @Test
  public void canAddProductToWarehouse() {
    Warehouse warehouse = new Warehouse("Test warehouse");
    Product product = new Product(
            "1",
            "Film",
            Category.THRILLER,
            10,
            LocalDate.now(),
            LocalDate.now());

    warehouse.addProduct(product);
    List<Product> products = warehouse.getAllProducts();

    assertThat(products)
            .hasSize(1)
            .contains(product);

//    assertEquals(1, warehouse.getAllProducts().size());
//    assertEquals("Film", warehouse.getAllProducts().getFirst().getName());
//    assertEquals(Category.THRILLER, warehouse.getAllProducts().getFirst().getCategory());
  }

  @Test
  public void updateProductToWarehouse() {
    Warehouse warehouse = new Warehouse("Test warehouse");
  }
}
