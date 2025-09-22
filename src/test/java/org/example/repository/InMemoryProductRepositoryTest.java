package org.example.repository;

import org.example.entities.Category;
import org.example.entities.Product;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class InMemoryProductRepositoryTest {
    private final InMemoryProductRepository repository = new InMemoryProductRepository();

    @Test
    public void testAddingProducts() {
        addNewProductToWarehouse("23", "Pants", Category.CLOTHES, 5);

        assertEquals(1, repository.getAllProducts().size());
    }

    @Test
    public void testAddingProductsWithoutName() {
        // A product's name attribute must not be empty.
        assertThatThrownBy(() -> addNewProductToWarehouse("34", "",
                Category.PROVISIONS, 0))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testGettingAllProducts() {
        addNewProductToWarehouse("23", "Pants", Category.CLOTHES, 9);
        addNewProductToWarehouse("24", "Shirt", Category.CLOTHES, 8);
        addNewProductToWarehouse("25", "Hat", Category.CLOTHES, 3);

        assertEquals(3, repository.getAllProducts().size());
    }

    @Test
    public void testGettingProductById() {
        addNewProductToWarehouse("23", "Pants", Category.CLOTHES, 9);
        addNewProductToWarehouse("24", "Shirt", Category.CLOTHES, 8);

        Optional<Product> shirt = Optional
                .ofNullable(repository.getAllProducts().getLast());

        assertEquals(shirt, repository.getProductById("24"));
    }

    @Test
    public void testCannotFindProductById() {
        assertThatThrownBy(() -> repository.getProductById("25"))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    public void testUpdatingAProduct() {
        addNewProductToWarehouse("53", "Raisins",
                Category.PROVISIONS, 5);

        Product updatedRaisins = returnNewProduct("53", "Raisins",
                Category.PROVISIONS, 8);

        repository.updateProduct(updatedRaisins);
        assertThat(updatedRaisins)
                .isEqualTo(repository.getProductById("53").get());
    }

    @Test
    public void testCannotUpdateAProduct() {
        Product raisins = returnNewProduct("53", "Raisins",
                Category.PROVISIONS, 5);

        assertThatThrownBy(() -> repository.updateProduct(raisins))
                .isInstanceOf(NoSuchElementException.class);
    }

    private void addNewProductToWarehouse(String id, String name,
                                          Category category, int rating) {
        repository.addProduct(
                new Product.Builder()
                        .id(id)
                        .name(name)
                        .category(category)
                        .rating(9)
                        .build()
        );
    }

    private Product returnNewProduct(String id, String name,
                                     Category category, int rating) {
        return new Product.Builder()
                .id(id)
                .name(name)
                .category(category)
                .rating(rating)
                .build();
    }
}
