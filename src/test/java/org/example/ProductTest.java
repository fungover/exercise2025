package org.example;

import org.example.entities.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductTest {
    @Test
    @DisplayName("Can create a valid Product instance")
    public void canCreateProduct() {
        Product product = new Product();

        assertThat(product).isNotNull();
    }
}
