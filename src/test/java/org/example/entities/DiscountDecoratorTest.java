package org.example.entities;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

public class DiscountDecoratorTest {

    @Test
    // Testar att DiscountDecorator verkligen sänker priset med
    // rätt procent. Produkten kostar 1000.0 och det läggs på 20% rabatt.
    void discountDecorator_appliesPercentageDiscount() {
        LocalDate today = LocalDate.now();

        // Skapar en produkt med pris 1000.0
        Product laptop = Product.builder()
            .id("p-001")
            .name("Budget Laptop")
            .category(Category.COMPUTER)
            .rating(8)
            .createdDate(today)
            .modifiedDate(today)
            .price(1000.0)
            .build();

        // Wrappar produkten i en DiscountDecorator med 20% rabatt
        Sellable discountedLaptop = new DiscountDecorator(laptop, 20);

        // Originalproduktens pris ska fortfarande vara 1000.0
        assertThat(laptop.getPrice()).isEqualTo(1000.0);

        // Den dekorerade versionen ska ha priset 800.0
        assertThat(discountedLaptop.getPrice()).isEqualTo(800.0);

        // Övriga fält ska vara oförändrade
        assertThat(discountedLaptop.getId()).isEqualTo(laptop.getId());
        assertThat(discountedLaptop.getName()).isEqualTo(laptop.getName());
    }
}
