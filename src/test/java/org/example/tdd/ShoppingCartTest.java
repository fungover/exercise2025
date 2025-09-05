package org.example.tdd;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

public class ShoppingCartTest {
    ShoppingCart shoppingCart = new ShoppingCart();

    @Test
    public void addingProductToCart() {
        shoppingCart.add(new Product("Milk", 14.5f));
        List<Product> products = shoppingCart.products();

        assertThat(products)
                .hasSize(1)
                .containsExactly(new Product("Milk", 14.5f));
    }

    @Test
    public void calculateTotalCartPrice() {
        shoppingCart.add(new Product("Milk", 14.5f));
        shoppingCart.add(new Product("Milk", 14.5f));
        shoppingCart.add(new Product("Banana", 2.9f));

        assertThat(shoppingCart.totalPrice()).isEqualTo(31.9f);
    }

    @Test
    public void removeExistingProduct() {
        //Arrange - Given
        shoppingCart.add(new Product("Apple", 4.0f));
        //Act - When
        shoppingCart.removeProduct(new Product("Apple", 4.0f));
        //Assert - Then
        assertThat(shoppingCart.products()).isEmpty();
    }

    @Test
    public void removingNonExistingProductThrowsException() {
        assertThatThrownBy(() -> shoppingCart.removeProduct(new Product("Apple", 4.0f)))
                .isInstanceOf(IllegalArgumentException.class);
    }


}
