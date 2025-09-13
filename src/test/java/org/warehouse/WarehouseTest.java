package org.warehouse;

import org.junit.jupiter.api.Test;
import org.warehouse.entities.Category;
import org.warehouse.entities.Product;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
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

  Product product2 = new Product(
          "2",
          "A",
          Category.THRILLER,
          10,
          LocalDate.now(),
          LocalDate.now());

  Product product3 = new Product(
          "3",
          "B",
          Category.THRILLER,
          10,
          LocalDate.now(),
          LocalDate.now());

  Product oldProduct = new Product(
          "4", "OldFilm", Category.THRILLER, 7,
          LocalDate.of(2025, 8, 1), LocalDate.of(2025, 9, 9)
  );

  Product newProduct = new Product(
          "5", "NewFilm", Category.THRILLER, 8,
          LocalDate.of(2025, 9, 10), LocalDate.of(2025, 9, 11)
  );

  Product productSameId = new Product(
          "1",
          "Film2",
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
  public void canSeeIfProductIdIsUnique() {
    warehouse.addProduct(product);

    assertThatThrownBy(() -> warehouse.addProduct(productSameId))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("already exists");

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
  public void cantUpdateProductIfIdNotFound() {
    assertThatThrownBy(() -> warehouse.updateProduct("100", "Film", Category.DRAMA, 10))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("not found");
  }

  @Test
  public void canGetAllProducts() {
    warehouse.addProduct(product);
    List<Product> products = warehouse.getAllProducts();
  }

  @Test
  public void cantReturnProductIfIdNotFound() {
    assertThatThrownBy(() -> warehouse.getAllProducts().getFirst())
    .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("No products found");
  }

  @Test
  public void canGetProductById() {
    warehouse.addProduct(product);
    var product  = warehouse.getProductById("1");

    assertThat(product).isNotNull();
    assertThat(product.getId()).isEqualTo("1");
    assertThat(product.getName()).isEqualTo("Film");
    assertThat(product.getCategory()).isEqualTo(Category.THRILLER);
    assertThat(product.getCreatedDate()).isEqualTo(LocalDate.now());
  }

  @Test
  public void canGetProductsByCategorySorted() {

    warehouse.addProduct(product);
    warehouse.addProduct(product2);
    warehouse.addProduct(product3);

    var sorted = warehouse.getProductsByCategorySorted(Category.THRILLER);

    assertThat(sorted).isSortedAccordingTo(Comparator.comparing(Product::getName));

  }

  @Test
  public void canGetProductCreatedAfterDate() {
    warehouse.addProduct(oldProduct);
    warehouse.addProduct(newProduct);

    var createdAfter = warehouse.getProductsCreatedAfter(LocalDate.of(2025, 8, 2));

    assertThat(createdAfter).hasSize(1);
    assertThat(createdAfter.getFirst().getCreatedDate()).isEqualTo(LocalDate.of(2025, 9, 10));
  }

  @Test
  public void canGetProductsWithModifiedDates() {
    warehouse.addProduct(newProduct);
    warehouse.addProduct(oldProduct);
    warehouse.addProduct(product2);
    warehouse.addProduct(product3);

    var modifiedProduct = warehouse.getModifiedProduct();

    assertThat(modifiedProduct).hasSize(2);

  }
}
