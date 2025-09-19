package org.example.entities;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import java.time.LocalDate;

public class SellableTest {

    @Test
    void product_implementsSellable_andReturnsCorrectValue() {
        LocalDate today = LocalDate.now();

        Product product = Product.builder()
            .id("p-001")
            .name("Budget Laptop")
            .category(Category.COMPUTER)
            .rating(8)
            .createdDate(today)
            .modifiedDate(today)
            .price(999.99)
            .build();

        // Kolla att produkten är Sellable
        assertThat(product).isInstanceOf(Sellable.class);

        // Kolla att metoderna fungerar
        Sellable sellable = product;
        assertThat(sellable.getId()).isEqualTo("p-001");
        assertThat(sellable.getName()).isEqualTo("Budget Laptop");
        assertThat(sellable.getPrice()).isEqualTo(999.99);
    }
}
