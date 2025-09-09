package org.example.service;

import org.example.entities.Category;
import org.example.entities.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;


class WareHouseTest {

    private Warehouse warehouse;

    @BeforeEach void setUp() {
        warehouse = new Warehouse();
    }

    @Test
    @DisplayName("Tests for addProduct method")
    void addProduct() {
        LocalDate today = LocalDate.now();

        // Success case
        Product validProduct = new Product("5", "Valid", Category.FOOD, 5, today, today);
        warehouse.addProduct(validProduct);
        assertTrue(warehouse.getAllProducts().contains(validProduct), "Valid product should be added");

        // Failure: empty name
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                warehouse.addProduct(new Product("1", "", Category.FOOD, 2, today, today)));
        assertEquals("Name cannot be empty", exception.getMessage());

        // Failure: null product
        exception = assertThrows(IllegalArgumentException.class, () ->
                warehouse.addProduct(null));
        assertEquals("Product cannot be null", exception.getMessage());

        // Failure: duplicate ID
        Product duplicateIdProduct = new Product(
                validProduct.id(),
                "Duplicate",
                Category.ELECTRONICS,
                5,
                today,
                today
        );
        exception = assertThrows(IllegalArgumentException.class, () ->
                warehouse.addProduct(duplicateIdProduct));
        assertEquals("Product with this ID already exists", exception.getMessage());
    }

    @Test
    @DisplayName("Tests for updateProduct method")
    void updateProduct() {

        Clock fixedClock = Clock.fixed(
                LocalDate.of(2025, 9, 7).atStartOfDay(ZoneOffset.UTC).toInstant(),
                ZoneOffset.UTC
        );
        LocalDate today = LocalDate.now(fixedClock);

        Product beforeUpdate = new Product("5", "Valid", Category.FOOD, 5, today, today);

        warehouse.addProduct((beforeUpdate));
        warehouse.updateProduct("5", "Updated", Category.ELECTRONICS, 9);

        Product afterUpdate = warehouse.getProductById("5");

        assertThat(afterUpdate).isNotNull();
        assertThat(afterUpdate.id()).isEqualTo("5"); // Same id
        assertThat(afterUpdate.name()).isEqualTo("Updated");
        assertThat(afterUpdate.category()).isEqualTo(Category.ELECTRONICS);
        assertThat(afterUpdate.rating()).isEqualTo(9);
        assertThat(afterUpdate.createdDate()).isEqualTo(today); // createdDate should be unchanged

        assertThat(beforeUpdate.modifiedDate()).isNotEqualTo(afterUpdate.modifiedDate());
        assertThat(afterUpdate.modifiedDate()).isAfterOrEqualTo(LocalDate.now());
    }

    @Test
    @DisplayName("Tests for getAllProducts method")
    void getAllProducts() {

        Clock fixedClock = Clock.fixed(
                LocalDate.of(2025, 9, 8).atStartOfDay(ZoneOffset.UTC).toInstant(),
                ZoneOffset.UTC
        );
        LocalDate today = LocalDate.now(fixedClock);

        Product product1 = new Product("97", "Product97", Category.FOOD, 5, today, today);
        warehouse.addProduct(product1);

        Product product2 = new Product("98", "Product98", Category.ELECTRONICS, 10, today, today);
        warehouse.addProduct(product2);

        Product product3 = new Product("99", "Product99", Category.TOYS, 8, today, today);
        warehouse.addProduct(product3);

        List <Product> copyWarehouse = warehouse.getAllProducts();

        assertThat(copyWarehouse).isNotNull();
        assertThat(copyWarehouse.size()).isEqualTo(3);
        assertThat(copyWarehouse).containsExactlyInAnyOrder(product1, product2, product3);
        assertThat(copyWarehouse.stream().anyMatch(p -> p.id().equals("100"))).isFalse();
    }

    @Test
    @DisplayName("Test when list is empty in getAllProducts method, should return empty list")
    void getAllProductsEmptyList() {
        List <Product> productList = warehouse.getAllProducts();
        assertThat(productList.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("Tests for getProductById method")
    void getProductById() {

        Clock fixedClock = Clock.fixed(
                LocalDate.of(2025, 9, 8).atStartOfDay(ZoneOffset.UTC).toInstant(),
                ZoneOffset.UTC
        );
        LocalDate today = LocalDate.now(fixedClock);

        Product product1 = new Product("97", "Product1", Category.FOOD, 5, today, today);
        Product product2 = new Product("98", "Product2", Category.TOYS, 8, today, today);

        warehouse.addProduct(product1);
        warehouse.addProduct(product2);

        Product found1 = warehouse.getProductById("97");
        Product found2 = warehouse.getProductById("98");

        assertThat(found1).isNotNull();
        assertThat(found2).isNotNull();
        assertThat(found1.id()).isEqualTo("97");
        assertThat(found2.id()).isEqualTo("98");

        assertThatThrownBy(() -> warehouse.getProductById("1000"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Product with id 1000 does not exist");
    }

    @Test
    @DisplayName("Tests for getProductsByCategorySorted method")
    void getProductsByCategorySorted() {

        Clock fixedClock = Clock.fixed(
                LocalDate.of(2025, 9, 8).atStartOfDay(ZoneOffset.UTC).toInstant(),
                ZoneOffset.UTC
        );
        LocalDate today = LocalDate.now(fixedClock);

        Product product1 = new Product("1", "Product1", Category.ELECTRONICS, 1, today, today);
        Product product2 = new Product("2", "Socks", Category.CLOTHING, 2, today, today);
        Product product3 = new Product("3", "Product1", Category.FOOD, 3, today, today);
        Product product4 = new Product("4", "Hoodie", Category.CLOTHING, 4, today, today);
        Product product5 = new Product("5", "Trousers", Category.CLOTHING, 4, today, today);
        Product product6 = new Product("6", "Pants", Category.CLOTHING, 4, today, today);

        warehouse.addProduct(product1);
        warehouse.addProduct(product2);
        warehouse.addProduct(product3);
        warehouse.addProduct(product4);
        warehouse.addProduct(product5);
        warehouse.addProduct(product6);

        List <Product> filteredList =  warehouse.getProductsByCategorySorted(Category.CLOTHING);
        List <Product> filteredListToys =  warehouse.getProductsByCategorySorted(Category.TOYS);

        assertThat(warehouse.getAllProducts().size()).isEqualTo(6);

        System.out.println(warehouse.getProductsByCategorySorted(Category.CLOTHING));
        assertThat(filteredList.size()).isEqualTo(4);
        assertThat(filteredListToys.size()).isEqualTo(0);
    }


}