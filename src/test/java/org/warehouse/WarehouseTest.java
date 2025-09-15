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

  Product product = new Product.Builder()
          .setId("1")
          .setName("Film")
          .setCategory(Category.THRILLER)
          .setRating(10)
          .setCreatedDate(LocalDate.now())
          .setModifiedDate(LocalDate.now())
          .build();

  Product product2 = new Product.Builder()
          .setId("2")
          .setName("Film2")
          .setCategory(Category.THRILLER)
          .setRating(10)
          .setCreatedDate(LocalDate.now())
          .setModifiedDate(LocalDate.now())
          .build();

  Product product3 = new Product.Builder()
          .setId("3")
          .setName("Film3")
          .setCategory(Category.THRILLER)
          .setRating(10)
          .setCreatedDate(LocalDate.now())
          .setModifiedDate(LocalDate.now())
          .build();

  Product oldProduct = new Product.Builder()
          .setId("4")
          .setName("OldFilm")
          .setCategory(Category.THRILLER)
          .setRating(7)
          .setCreatedDate(LocalDate.of(2025, 8, 1))
          .setModifiedDate(LocalDate.of(2025, 9, 9))
          .build();

  Product newProduct = new Product.Builder()
          .setId("5")
          .setName("NewFilm")
          .setCategory(Category.THRILLER)
          .setRating(7)
          .setCreatedDate(LocalDate.of(2025, 9, 10))
          .setModifiedDate(LocalDate.of(2025, 9, 11))
          .build();

  Product productSameId = new Product.Builder()
          .setId("1")
          .setName("Film2")
          .setCategory(Category.THRILLER)
          .setRating(10)
          .setCreatedDate(LocalDate.now())
          .setModifiedDate(LocalDate.now())
          .build();

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

    assertThat(updated.name()).isEqualTo("Film2");
    assertThat(updated.category()).isEqualTo(Category.DRAMA);
    assertThat(product.createdDate()).isEqualTo(updated.createdDate());
    assertThat(updated.modifiedDate()).isEqualTo(LocalDate.now());
    assertThat(updated.rating()).isEqualTo(9);
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
    assertThat(products)
            .hasSize(1)
            .contains(product);
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
    var found  = warehouse.getProductById("1");

    assertThat(found).isNotNull();
    assertThat(found.id()).isEqualTo("1");
    assertThat(found.name()).isEqualTo("Film");
    assertThat(found.category()).isEqualTo(Category.THRILLER);
    assertThat(found.createdDate()).isEqualTo(LocalDate.now());
  }

  @Test
  public void cantGetProductByIdIfIdNotFound() {
    assertThatThrownBy(() -> warehouse.getProductById("100"))
    .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("not found");
  }

  @Test
  public void canGetProductsByCategorySorted() {

    warehouse.addProduct(product);
    warehouse.addProduct(product2);
    warehouse.addProduct(product3);

    var sorted = warehouse.getProductsByCategorySorted(Category.THRILLER);

    assertThat(sorted).isSortedAccordingTo(Comparator.comparing(Product::name));

  }

  @Test
  public void cantGetProductsByCategorySortedWithEmptyList() {
    assertThatThrownBy(() -> warehouse.getProductsByCategorySorted(Category.DRAMA))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("No products found in category");

  }

  @Test
  public void canGetProductCreatedAfterDate() {
    warehouse.addProduct(oldProduct);
    warehouse.addProduct(newProduct);

    var createdAfter = warehouse.getProductsCreatedAfter(LocalDate.of(2025, 8, 2));

    assertThat(createdAfter).hasSize(1);
    assertThat(createdAfter.getFirst().createdDate()).isEqualTo(LocalDate.of(2025, 9, 10));
  }

  @Test
  public void cantGetProductCreatedAfterDateIfProductNotExist() {
    assertThatThrownBy(() -> warehouse.getProductsCreatedAfter(LocalDate.of(2025, 8, 2)))
    .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("No products found");
  }

  @Test
  public void canGetProductsWithModifiedDates() {
    warehouse.addProduct(newProduct);
    warehouse.addProduct(oldProduct);
    warehouse.addProduct(product2);

    var modifiedProduct = warehouse.getModifiedProducts();

    assertThat(modifiedProduct).hasSize(2);
  }

  @Test
  public void cantFindProductsWithModifiedDates() {
    warehouse.addProduct(product);
    warehouse.addProduct(product2);

    assertThatThrownBy(() -> warehouse.getModifiedProducts())
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("No modified products were found");
  }
}
