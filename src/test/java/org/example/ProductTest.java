package org.example;

import org.example.entities.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductTest {

    @Test
    @DisplayName("Can create a Product with a unique ID")
    public void canCreateProductWithId(){
        Product product = new Product(1);
        assertThat(product.getId()).isEqualTo(1);
    }


    @Test
    @DisplayName("Can create a Product with a name")
    public void canCreateProductWithName(){
        Product product = new Product(1, "Laptop");
        assertThat(product.getName()).isEqualTo("Laptop");
    }
}
