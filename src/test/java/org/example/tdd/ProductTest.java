package org.example.tdd;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductTest {

    @Test
    void canCreateProduct() {
    Product product = new Product("ID","");
    }

    @Test
    void canCreateProductID() {
        Product product = new Product("ID","");
        assertEquals("ID",product.ID());
    }

    @Test
    void cantCreateProductID() {
        Product product = new Product("","");
        assertEquals("ID",product.ID());
    }

    @Test
    void canCreateProductName() {
        Product product = new Product("ID","Name");
        assertEquals("Name",product.name());
    }

    @Test
    void cantCreateProductName() {
        Product product = new Product("ID","");
        assertEquals("Name",product.name());
    }














}