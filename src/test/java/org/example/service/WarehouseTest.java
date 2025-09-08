package org.example.service;

import org.example.entities.Category;
import org.example.entities.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class WarehouseTest {

    private Warehouse warehouse;

    @BeforeEach void warehouseSetup() {
        warehouse = new Warehouse(); //before each test
    }

    @Test void productAddedToWareHouseInventory() {
        Product laptop = new Product("Laptop", Category.ELECTRONICS, 4);
        assertTrue(warehouse.addProduct(laptop));
    }

    @Test void productAddedWithEmptyName() {
        Warehouse warehouse = new Warehouse();
        Product laptop = new Product("", Category.ELECTRONICS, 4);
        assertFalse(warehouse.addProduct(laptop));

    }

    @Test void updateProduct_Valid() {
        Product lamp = new Product("lamp", Category.ELECTRONICS, 10);
        warehouse.addProduct(lamp);

        String idAsString = String.valueOf(lamp.id());

        warehouse.getAllProducts()
                 .forEach(System.out::println);
        warehouse.updateProduct(idAsString, "blueLaptop", Category.FOOD, 3);


        //get our updated product
        Product updated = warehouse.getProductById(lamp.id()
                                                       .toString());

        assertEquals(lamp.id(), updated.id());
        assertEquals("blueLaptop", updated.name());
        assertEquals(Category.FOOD, updated.category());
        assertEquals(3, updated.rating());
    }

    @Test void updateProduct_Invalid() {
        Product lamp = new Product("lamp", Category.ELECTRONICS, 10);
        warehouse.addProduct(lamp);
        assertThrows(IllegalArgumentException.class,
          () -> warehouse.updateProduct("3", "blueLaptop", Category.FOOD, 3));
    }

    @Test void getAllProducts_Valid() {
        Product lamp = new Product("lamp", Category.ELECTRONICS, 10);
        Product cpu = new Product("cpu", Category.ELECTRONICS, 2);

        warehouse.addProduct(lamp);
        warehouse.addProduct(cpu);

        List<Product> products = warehouse.getAllProducts();
        assertEquals(2, products.size());

    }

    @Test void getAllProducts_ShouldUpdateAfterRemoval() {
        Product lamp = new Product("lamp", Category.ELECTRONICS, 10);
        Product cpu = new Product("cpu", Category.ELECTRONICS, 2);

        warehouse.addProduct(lamp);
        warehouse.addProduct(cpu);

        warehouse.removeProductById(cpu.id()
                                       .toString());

        List<Product> products = warehouse.getAllProducts();
        assertEquals(1, products.size());
        assertFalse(products.contains(cpu));

    }

    @Test void getAllProducts_WhenEmpty_ShouldReturnEmptyList() {
        List<Product> products = warehouse.getAllProducts();
        assertEquals(0, products.size());
        assertNotNull(products);
        assertTrue(products.isEmpty());

    }

    @Test void getProductsByCategorySorted_valid() {
        Product lamp = new Product("lamp", Category.ELECTRONICS, 10);
        Product pc = new Product("PC", Category.ELECTRONICS, 4);
        Product dogFood = new Product("DogFood", Category.FOOD, 2);
        Product catFood = new Product("CatFood", Category.FOOD, 7);
        warehouse.addProduct(lamp);
        warehouse.addProduct(pc);
        warehouse.addProduct(dogFood);
        warehouse.addProduct(catFood);

        List<Product> sortedByCategory = warehouse.getProductsByCategorySorted(
          Category.FOOD);

        assertEquals(2, sortedByCategory.size());

        assertEquals("CatFood", sortedByCategory.get(0)
                                                .name());
        assertEquals("DogFood", sortedByCategory.get(1)
                                                .name());
    }

    @Test void getProductsByCategorySorted_invalid() {
        Product lamp = new Product("lamp", Category.ELECTRONICS, 10);
        Product pc = new Product("PC", Category.ELECTRONICS, 4);

        warehouse.addProduct(lamp);
        warehouse.addProduct(pc);

        List<Product> sortedByCategory = warehouse.getProductsByCategorySorted(
          Category.FOOD);

        assertEquals(0, sortedByCategory.size(),
          "Should be 0, we have no Food items in our inventory");
        assertNotNull(sortedByCategory);
        assertTrue(sortedByCategory.isEmpty());
    }

    @Test void getProductsCreatedAfter_valid() {
        LocalDate testDate = LocalDate.now(); //todays date
        LocalDate daysFromCheck5 = LocalDate.now() // 5 After today
                                            .plusDays(5);

        Product lamp = new Product("lamp", Category.ELECTRONICS, 10, daysFromCheck5);
        Product pc = new Product("PC", Category.ELECTRONICS, 4, daysFromCheck5);
        Product ps5 = new Product("Super Delux Playstaion", Category.ELECTRONICS, 4);

        warehouse.addProduct(lamp);
        warehouse.addProduct(pc);
        warehouse.addProduct(ps5);
        List<Product> productsCreaetedAfterList = warehouse.getProductsCreatedAfter(
          testDate);
        assertEquals(2, productsCreaetedAfterList.size());
    }

    @Test void getProductsCreatedAfter_invalid() {
        LocalDate testDate = LocalDate.now(); // todays date

        Product lamp = new Product("lamp", Category.ELECTRONICS, 10,
          testDate.minusDays(1));
        Product pc = new Product("PC", Category.ELECTRONICS, 4,
          testDate.minusDays(2));
        Product ps5 = new Product("Super Delux Playstaion", Category.ELECTRONICS, 6,
          testDate.minusDays(3));

        warehouse.addProduct(lamp);
        warehouse.addProduct(pc);
        warehouse.addProduct(ps5);
        List<Product> productsCreatedAfterList = warehouse.getProductsCreatedAfter(
          testDate);
        assertEquals(0, productsCreatedAfterList.size());

    }

}