package org.example.service;

import org.example.entities.Category;
import org.example.entities.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class WarehouseTest {

    private Warehouse warehouse;

    @BeforeEach
    void setUp() {
        warehouse = new Warehouse();
    }

    @Test
    @DisplayName( "Add a product to the warehouse")
    void addProductToWarehouse(){
        ZonedDateTime now = ZonedDateTime.now();
        Product clicker = ( new Product(
                "clicker-001",
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
    @DisplayName("Throws an exception when addProduct name is empty")
    void addProductToWarehouseWithEmptyName_throwsException(){
        Product productWithEmptyName = new Product(
                "p-001",
                "", //
                Category.FOOD,
                3,
                ZonedDateTime.now(),
                ZonedDateTime.now()
        );

        assertThatThrownBy(() -> warehouse.addProduct(productWithEmptyName))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Product name cannot be empty or null.");
    }

    @Test
    @DisplayName("Returns all added products to Warehouse")
    public void getAllProducts_returnsAllAddedProducts() {
        Warehouse warehouse = new Warehouse();

        Product chewBone = new Product(
                "p-001",
                "Chew Bone",
                Category.FOOD,
                4,
                ZonedDateTime.now(),
                ZonedDateTime.now());

        Product SqueakyBanana = new Product(
                "p-002",
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

}