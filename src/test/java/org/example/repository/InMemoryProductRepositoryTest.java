package org.example.repository;

import org.example.entities.Category;
import org.example.entities.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

//trying new naming, to be more precise
class InMemoryProductRepositoryTest {
    private ProductRepository repository;

    @BeforeEach void setUp() {
        repository = new InMemoryProductRepository();
    }

    @Test void addProduct_ShouldAddSuccessfully() {
        Product TestProduct = new Product.Builder().name("Test Product")
                                                   .category(Category.ELECTRONICS)
                                                   .rating(5)
                                                   .build();

        boolean result = repository.addProduct(TestProduct);

        assertTrue(result);
        assertEquals(1, repository.getAllProducts()
                                  .size());
    }

    @Test void getProductById_WhenExists_ShouldReturnProduct() {
        Product TestProduct = new Product.Builder().name("Test Product")
                                                   .category(Category.ELECTRONICS)
                                                   .rating(5)
                                                   .build();

        repository.addProduct(TestProduct);

        Optional<Product> found = repository.getProductById(
          String.valueOf(TestProduct.id()));

        assertTrue(found.isPresent());
        assertEquals(TestProduct.id(), found.get()
                                            .id());
    }

    @Test void getProductById_whenNotExists_ShouldReturnEmpty() {
        Optional<Product> found = repository.getProductById("69");

        assertTrue(found.isEmpty());
    }

    @Test void updateProduct_ShouldUpdateSuccessfully() {
        Product original = new Product.Builder().name("Original")
                                                .category(Category.ELECTRONICS)
                                                .rating(5)
                                                .build();

        repository.addProduct(original);

        Product updated = new Product.Builder().name("Updated")
                                               .id(original.id())
                                               .category(Category.FOOD)
                                               .rating(8)
                                               .build();

        repository.getAllProducts()
                  .forEach(System.out::println);
        repository.updateProduct(updated);
        repository.getAllProducts()
                  .forEach(System.out::println);

        Optional<Product> found = repository.getProductById(updated.id()
                                                                   .toString());
        assertTrue(found.isPresent());
        assertEquals("Updated", found.get()
                                     .name());

    }

    @Test void findByCategory_ShouldReturnCorrectProducts() {
        Product electronics = new Product.Builder().name("Laptop")
                                                   .category(Category.ELECTRONICS)
                                                   .rating(5)
                                                   .build();

        Product food = new Product.Builder().name("AngryFish")
                                            .category(Category.FOOD)
                                            .rating(4)
                                            .build();


        repository.addProduct(electronics);
        repository.addProduct(food);

        List<Product> products = repository.findByCategory(Category.FOOD);

        assertEquals(1, products.size());
        assertEquals("AngryFish", products.get(0)
                                          .name());
    }


}