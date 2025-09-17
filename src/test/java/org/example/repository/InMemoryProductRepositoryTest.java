package org.example.repository;

import org.example.entities.Category;
import org.example.entities.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

public class InMemoryProductRepositoryTest {
    private InMemoryProductRepository repo;

    //Helper Methods
    public Product createProduct(String id, String name, Category category, int rating, double price) {
        Product product = new Product.Builder()
                .id(id)
                .name(name)
                .category(category)
                .rating(rating)
                .price(price)
                .build();

        return product;
    }

    //Setup
    @BeforeEach
    public void setup() {
        repo = new InMemoryProductRepository();
    }

    //Tests
    @Test
    @DisplayName("addProduct: adds a Product")
    public void canAddProduct() {
    Product testProduct = createProduct("1", "Test", Category.GENERAL, 1, 99.0);
    repo.addProduct(testProduct);

    assertThat(repo.getAllProducts())
            .hasSize(1)
            .first()
            .extracting(Product::getId, Product::getName, Product::getCategory, Product::getRating, Product::getPrice)
            .containsExactly("1", "Test", Category.GENERAL, 1, 99.0);
    }

    @ParameterizedTest
    @MethodSource("addProductTestData")
    @DisplayName("addProduct: can't add a Product with the same id or name")
    public void cannotAddProductWithSameIdOrName(Product duplicateProduct) {
        Product testProduct = createProduct("1", "Test", Category.GENERAL, 1, 99);
        repo.addProduct(testProduct);

        assertThatThrownBy(() -> repo.addProduct(duplicateProduct))
        .isInstanceOf(IllegalArgumentException.class);
    }

    private static Stream<Arguments> addProductTestData() {
        return Stream.of(
                Arguments.of(new Product.Builder()
                        .id("1").name("Othername").category(Category.GENERAL).rating(2).price(50).build()),
                Arguments.of(new Product.Builder()
                        .id("2").name("Test").category(Category.GENERAL).rating(2).price(50).build())
        );
    }

    @Test
    @DisplayName("updateProduct: can update an existing Product")
    public void canUpdateProduct() {
        Product testProduct = createProduct("1", "Test", Category.GENERAL, 1, 99);
        repo.addProduct(testProduct);

        Product updatedProduct = createProduct("1", "Updated", Category.ELECTRONICS, 2, 100);
        repo.updateProduct(updatedProduct);

        assertThat(repo.getAllProducts())
                .hasSize(1)
                .first()
                .extracting(Product::getId, Product::getName, Product::getCategory, Product::getRating, Product::getPrice)
                .containsExactly("1", "Updated", Category.ELECTRONICS, 2, 100.0);
    }

    @Test
    @DisplayName("updateProduct: can't update an existing Product if input is null")
    public void cannotUpdateProductIfProductIsNull() {
    assertThatThrownBy(() -> repo.updateProduct(null))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("getAllProducts: can get all Products")
    public void canGetAllProducts() {
        Product testProduct = createProduct("1", "Test1", Category.GENERAL, 1, 100.0);
        Product testProduct2 = createProduct("2", "Test2", Category.GENERAL, 2, 100.0);
        Product testProduct3 = createProduct("3", "Test3", Category.GENERAL, 3, 100.0);

        repo.addProduct(testProduct);
        repo.addProduct(testProduct2);
        repo.addProduct(testProduct3);

        assertThat(repo.getAllProducts())
                .hasSize(3)
                .extracting(Product::getId, Product::getName, Product::getCategory, Product::getRating, Product::getPrice)
                .containsExactly(
                        tuple("1", "Test1", Category.GENERAL, 1, 100.0),
                        tuple("2", "Test2", Category.GENERAL, 2, 100.0),
                        tuple("3", "Test3", Category.GENERAL, 3, 100.0)
                );

    }

    @Test
    @DisplayName("getAllProducts: return an empty list if list is empty")
    public void canGetAllProductsEmptyList() {
        assertThat(repo.getAllProducts()).isEmpty();
    }

    @Test
    @DisplayName("getProductById: can get a Product by id")
    public void canGetProductById() {
        Product testProduct = createProduct("1", "Test", Category.GENERAL, 1, 99.0);
        Product testProduct2 = createProduct("2", "Test2", Category.GENERAL, 2, 100.0);

        repo.addProduct(testProduct);
        repo.addProduct(testProduct2);

        assertThat(repo.getProductById("1"))
                .isPresent()
                .get()
                .extracting(Product::getId,  Product::getName, Product::getCategory, Product::getRating, Product::getPrice)
                .containsExactly("1", "Test", Category.GENERAL, 1, 99.0);
    }

    @Test
    @DisplayName("getProductById: returns empty Optional when product does not exist")
    public void canGetProductByIdOptional() {
        assertThat(repo.getProductById("1"))
                .isNotPresent();
    }

}
