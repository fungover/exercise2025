package org.example.entities;

import org.example.repository.InMemoryProductRepository;
import org.example.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DiscountDecoratorTest {
    private ProductRepository repository;

    @BeforeEach void setUp() {
        repository = new InMemoryProductRepository();
    }

    @Test void decoratorPattern_AppliesDiscountCorrectly() {
        Product laptop = new Product.Builder().name("Super Laptop")
                                              .category(Category.ELECTRONICS)
                                              .rating(9)
                                              .price(1000)
                                              .build();

        //wrap it
        Sellable discountedLaptop = new DiscountDecorator(laptop, 20.0);//20% off

        //original
        assertEquals("Super Laptop", laptop.getName());
        assertEquals(1000, laptop.getPrice());

        //discount
        assertEquals("Super Laptop", discountedLaptop.getName());
        assertEquals(800.0, discountedLaptop.getPrice());
    }
}