package org.warehouse;

import org.junit.jupiter.api.Test;
import org.warehouse.entities.Category;
import org.warehouse.entities.Product;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class WarehouseTest {
  Warehouse warehouse = new Warehouse("Test warehouse");

  Product product = new Product(
          "1",
          "Film",
          Category.THRILLER,
          10,
          LocalDate.now(),
          LocalDate.now());

  @Test
  public void canCreateWarehouseAndGetName() {

    assertEquals(warehouse.getName(), "Test warehouse");
  }

  @Test
  public void canAddProductToWarehouse() {

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
  public void canUpdateProduct() {

    warehouse.addProduct(product);

    warehouse.updateProduct("1", "Film2", Category.DRAMA, 9);
    Product updated = warehouse.getAllProducts().getFirst();

    assertThat(updated.getName()).isEqualTo("Film2");
    assertThat(updated.getCategory()).isEqualTo(Category.DRAMA);
    assertThat(product.getCreatedDate()).isEqualTo(updated.getCreatedDate());
    assertThat(product.getModifiedDate()).isEqualTo(LocalDate.now());
  }

  @Test
  public void canGetAllProducts() {
    List<Product> products = warehouse.getAllProducts();
  }
}
