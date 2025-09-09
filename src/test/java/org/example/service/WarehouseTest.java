package org.example.service;

import org.example.entities.Category;
import org.example.entities.Product;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class WarehouseTest {
    private final Warehouse warehouse = new Warehouse();

    @Test
    public void testAddingProducts() {
        warehouse.addProduct(new Product("23", "Pants",
                Category.CLOTHES, 5, LocalDateTime.now(), null));

        assertEquals(1, warehouse.getAllProducts().size());
    }
    @Test
    public void testAddingProductsWithoutName() {
        // A product's name attribute must not be empty.
        warehouse.addProduct(new Product("34", "", Category.PROVISIONS,
                0, LocalDateTime.now(), null));

        assertEquals(0, warehouse.getAllProducts().size());
    }

    @Test
    public void testGettingAllProducts() {
        warehouse.addProduct(new Product("23", "Pants",
                Category.CLOTHES, 9, LocalDateTime.now(), null));
        warehouse.addProduct(new Product("24", "Shirt",
                Category.CLOTHES, 8, LocalDateTime.now(), null));
        warehouse.addProduct(new Product("25", "Hat",
                Category.CLOTHES, 3, LocalDateTime.now(), null));

        assertEquals(3, warehouse.getAllProducts().size());
    }

    @Test
    public void testGettingProductById() {
        warehouse.addProduct(new Product("23", "Pants",
                Category.CLOTHES, 9, LocalDateTime.now(), null));
        warehouse.addProduct(new Product("24", "Shirt",
                Category.CLOTHES, 8, LocalDateTime.now(), null));

        Product shirt = warehouse.getAllProducts().getLast();

        assertEquals(shirt, warehouse.getProductById("24"));
    }
    @Test
    public void testCannotFindProductById() {
        warehouse.addProduct(new Product("23", "Pants",
                Category.CLOTHES, 9, LocalDateTime.now(), null));
        warehouse.addProduct(new Product("24", "Shirt",
                Category.CLOTHES, 8, LocalDateTime.now(), null));

        assertThatThrownBy(() -> warehouse.getProductById("25"))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    public void testGettingProductsByCategorySorted() {
        // Sorted by name A-Z.
        Product pants = new Product("23", "Pants",
                Category.CLOTHES, 9, LocalDateTime.now(), null);
        Product shirt = new Product("24", "Shirt",
                Category.CLOTHES, 8, LocalDateTime.now(), null);
        Product hat = new Product("25", "Hat",
                Category.CLOTHES, 3, LocalDateTime.now(), null);

        warehouse.addProduct(pants); // index 0
        warehouse.addProduct(shirt); // index 1
        warehouse.addProduct(hat); // index 2

        List<Product> sortedList = new ArrayList<>();
        sortedList.add(0, hat);
        sortedList.add(1, pants);
        sortedList.add(2, shirt);

        assertEquals(sortedList,
                warehouse.getProductsByCategorySorted(Category.CLOTHES));
    }
    @Test
    public void testCannotFindProductsByCategory() {
        Product raisins = new Product("53", "Raisins",
                Category.PROVISIONS, 7, LocalDateTime.now(), null);
        Product banana = new Product("54", "Banana",
                Category.PROVISIONS, 8, LocalDateTime.now(), null);

        warehouse.addProduct(raisins); // index 0
        warehouse.addProduct(banana); // index 1

        List<Product> sortedList = new ArrayList<>();
        sortedList.add(0, banana);
        sortedList.add(1, raisins);

        // We are trying to sort clothes, but only have provisions in our products.
        assertNotEquals(sortedList,
                warehouse.getProductsByCategorySorted(Category.CLOTHES));
    }
}
