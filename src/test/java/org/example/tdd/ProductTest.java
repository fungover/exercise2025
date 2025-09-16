package org.example.tdd;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductTest {

    @Test
    void canCreateProduct() {
    Product product = new Product("ID","", Category.FOOD, 5);
    }

    @Test
    public void canCreateProductID() {
        Product product = new Product("ID","", Category.FOOD, 5);
        assertEquals("ID",product.ID());
    }

    @Test
    public void cantCreateProductID() {
        Product product = new Product("","", Category.FOOD, 5);
        assertEquals("ID",product.ID());
    }

    @Test
    public void canCreateProductName() {
        Product product = new Product("ID","Name", Category.FOOD, 5);
        assertEquals("Name",product.name());
    }

    @Test
    public void cantCreateProductName() {
        Product product = new Product("ID","", Category.FOOD, 5);
        assertEquals("Name",product.name());
    }

    @Test
    public void canCreateProductCategory() {
        Product product = new Product("ID","Name",Category.FOOD, 5);
        assertEquals(Category.FOOD,product.category());
    }

    @Test
    public void cantCreateProductCategory() {
        Product product = new Product("ID","Name",null, 5);
        assertEquals(Category.FOOD,product.category());
    }















}