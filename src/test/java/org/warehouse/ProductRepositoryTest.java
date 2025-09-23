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

public class ProductRepositoryTest {
  ProductRepository repository = new InMemoryProductRepository();
  ProductService productService = new ProductService(repository);

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

  Product updatedProduct = new Product.Builder()
          .setId("1")
          .setName("Film2")
          .setCategory(Category.DRAMA)
          .setRating(9)
          .setCreatedDate(LocalDate.now())
          .setModifiedDate(LocalDate.now())
          .build();

  Product NotFoundProduct = new Product.Builder()
          .setId("100")
          .setName("Film")
          .setCategory(Category.DRAMA)
          .setRating(9)
          .setCreatedDate(LocalDate.now())
          .setModifiedDate(LocalDate.now())
          .build();

  @Test
  public void canAddProductToWarehouse() {

    productService.addProduct(product);
    List<Product> products = productService.getAllProducts();

    assertThat(products)
            .hasSize(1)
            .contains(product);

//    assertEquals(1, warehouse.getAllProducts().size());
//    assertEquals("Film", warehouse.getAllProducts().getFirst().getName());
//    assertEquals(Category.THRILLER, warehouse.getAllProducts().getFirst().getCategory());
  }

  @Test
  public void canSeeIfProductIdIsUnique() {
    productService.addProduct(product);

    assertThatThrownBy(() -> productService.addProduct(productSameId))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("already exists");

  }

  @Test
  public void canUpdateProduct() {

    productService.addProduct(product);

    productService.updateProduct(updatedProduct);
    Product updated = productService.getAllProducts().getFirst();

    assertThat(updated.name()).isEqualTo("Film2");
    assertThat(updated.category()).isEqualTo(Category.DRAMA);
    assertThat(product.createdDate()).isEqualTo(updated.createdDate());
    assertThat(updated.modifiedDate()).isEqualTo(LocalDate.now());
    assertThat(updated.rating()).isEqualTo(9);
  }

  @Test
  public void cantUpdateProductIfIdNotFound() {
    assertThatThrownBy(() -> productService.updateProduct(NotFoundProduct))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("not found");
  }

  @Test
  public void canGetAllProducts() {
    productService.addProduct(product);
    List<Product> products = productService.getAllProducts();
    assertThat(products)
            .hasSize(1)
            .contains(product);
  }

  @Test
  public void cantReturnProductIfIdNotFound() {
    assertThatThrownBy(() -> productService.getAllProducts().getFirst())
    .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("No products found");
  }

  @Test
  public void canGetProductById() {
    productService.addProduct(product);
    var found  = productService.getProductById("1");

    assertThat(found).isNotNull();
    assertThat(found.id()).isEqualTo("1");
    assertThat(found.name()).isEqualTo("Film");
    assertThat(found.category()).isEqualTo(Category.THRILLER);
    assertThat(found.createdDate()).isEqualTo(LocalDate.now());
  }

  @Test
  public void cantGetProductByIdIfIdNotFound() {
    assertThatThrownBy(() -> productService.getProductById("100"))
    .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("not found");
  }

  @Test
  public void canGetProductsByCategorySorted() {

    productService.addProduct(product);
    productService.addProduct(product2);
    productService.addProduct(product3);

    var sorted = productService.getProductsByCategorySorted(Category.THRILLER);

    assertThat(sorted).isSortedAccordingTo(Comparator.comparing(Product::name));

  }

  @Test
  public void cantGetProductsByCategorySortedWithEmptyList() {
    assertThatThrownBy(() -> productService.getProductsByCategorySorted(Category.DRAMA))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("No products found in category");

  }

  @Test
  public void canGetProductCreatedAfterDate() {
    productService.addProduct(oldProduct);
    productService.addProduct(newProduct);

    var createdAfter = productService.getProductsCreatedAfter(LocalDate.of(2025, 8, 2));

    assertThat(createdAfter).hasSize(1);
    assertThat(createdAfter.getFirst().createdDate()).isEqualTo(LocalDate.of(2025, 9, 10));
  }

  @Test
  public void cantGetProductCreatedAfterDateIfProductNotExist() {
    assertThatThrownBy(() -> productService.getProductsCreatedAfter(LocalDate.of(2025, 8, 2)))
    .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("No products found");
  }

  @Test
  public void canGetProductsWithModifiedDates() {
    productService.addProduct(newProduct);
    productService.addProduct(oldProduct);
    productService.addProduct(product2);

    var modifiedProduct = productService.getModifiedProducts();

    assertThat(modifiedProduct).hasSize(2);
  }

  @Test
  public void cantFindProductsWithModifiedDates() {
    productService.addProduct(product);
    productService.addProduct(product2);

    assertThatThrownBy(() -> productService.getModifiedProducts())
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("No modified products were found");
  }
}
