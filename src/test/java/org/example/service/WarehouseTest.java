package org.example.service;

import org.example.entities.Category;
import org.example.entities.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class WarehouseTest {

    private Warehouse warehouse;

    @BeforeEach
    void setUp() {
        warehouse = new Warehouse();
    }

    @Test
    @DisplayName( "Adds a product to the warehouse")
    void addProductToWarehouse(){
        ZonedDateTime now = ZonedDateTime.now();
        Product clicker = ( new Product(
                UUID.randomUUID().toString(),
                "Clicker",
                Category.TRAINING,
                5,
                now,
                now
        ));

        warehouse.addProduct(clicker);

        List<Product> products = warehouse.getAllProducts();

        assertThat(products)
                .hasSize(1)
                .containsExactly(clicker);
    }

    @Test
    @DisplayName("Throws an exception when name is empty")
    void addProductToWarehouseWithEmptyName_throwsException(){
        assertThatThrownBy(() -> warehouse.addProduct(
                new Product(
                        UUID.randomUUID().toString(),
                        "",
                        Category.FOOD,
                        3,
                        ZonedDateTime.now(),
                        ZonedDateTime.now()
                )))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Product name cannot be empty or null.");

    }

    @Test
    @DisplayName("Returns all added products to  the warehouse")
    public void getAllProducts_returnsAllAddedProducts() {
        Warehouse warehouse = new Warehouse();

        Product chewBone = new Product(
                UUID.randomUUID().toString(),
                "Chew Bone",
                Category.FOOD,
                4,
                ZonedDateTime.now(),
                ZonedDateTime.now());

        Product SqueakyBanana = new Product(
                UUID.randomUUID().toString(),
                "Squeaky Banana",
                Category.TOYS,
                3,
                ZonedDateTime.now(),
                ZonedDateTime.now());

        warehouse.addProduct(chewBone);
        warehouse.addProduct(SqueakyBanana);

        List<Product> allProducts = warehouse.getAllProducts();

        assertThat(allProducts)
                .hasSize(2)
                .containsExactly(chewBone, SqueakyBanana);
    }

    @Test
    @DisplayName("Throws an exception when trying to modify returned list")
    public void getAllProducts_throwsException_whenTryingToModifyReturnedList() {
        Warehouse warehouse = new Warehouse();
        Product redBlanket = new Product(
                UUID.randomUUID().toString(),
                "Red Blanket",
                Category.BEDDING,
                4,
                ZonedDateTime.now(),
                ZonedDateTime.now());

        warehouse.addProduct(redBlanket);

        List<Product> allProducts = warehouse.getAllProducts();

        assertThatThrownBy(() -> allProducts.add(new Product(
                UUID.randomUUID().toString(),
                "Squeaky Banana",
                Category.TOYS,
                3,
                ZonedDateTime.now(),
                ZonedDateTime.now())))
                .isInstanceOf(UnsupportedOperationException.class);

        assertThat(allProducts)
                .hasSize(1)
                .containsExactly(redBlanket);

    }

    @Test
    @DisplayName("Returns product by valid ID")
    void getProductById_returnsProduct_whenIdExists() {
        Product chewBone = new Product(
                UUID.randomUUID().toString(),
                "Chew Bone",
                Category.FOOD,
                4,
                ZonedDateTime.now(),
                ZonedDateTime.now());

        warehouse.addProduct(chewBone);

        Optional<Product> result = warehouse.getProductById(chewBone.id());

        assertThat(result)
                .isPresent()
                .contains(chewBone);
    }

    @Test
    @DisplayName("Returns empty when product ID does not exist")
    void getProductById_returnsEmpty_whenIdDoesNotExist() {
        Optional<Product> result = warehouse.getProductById(UUID.randomUUID().toString());

        assertThat(result)
                .isEmpty();
    }

    @Test
    @DisplayName("Returns empty when ID is not a valid UUID")
    void getProductById_returnsEmpty_whenIdIsInvalidFormat() {
        Optional<Product> result = warehouse.getProductById("not-a-uuid");

        assertThat(result)
                .isEmpty();
    }
}