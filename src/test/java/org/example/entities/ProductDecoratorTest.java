package org.example.entities;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductDecoratorTest {

    @Test
    // Vi använder en enkel "TestDecorator" för att bekräfta att
    // ProductDecorator verkligen skickar vidare (delegerar) alla
    // anrop till det wrappade objektet (Product).
    void decoratorDelegatesCallsToWrappedProduct() {
        LocalDate today = LocalDate.now();

        // Skapar en vanlig produkt (Product implementerar Sellable)
        Product product = Product.builder()
            .id("p-001")
            .name("Budget Laptop")
            .category(Category.COMPUTER)
            .rating(8)
            .createdDate(today)
            .modifiedDate(today)
            .price(999.99)
            .build();

        // Wrappar produkten med en dekorator som inte gör något extra
        Sellable decorator = new TestDecorator(product);

        // Kontrollera att alla anrop skickas vidare till originalprodukten
        assertThat(decorator.getId()).isEqualTo(product.getId());
        assertThat(decorator.getName()).isEqualTo(product.getName());
        assertThat(decorator.getPrice()).isEqualTo(product.getPrice());
    }

    // En "dummy"-dekorator för att testa ProductDecorator
    static class TestDecorator extends ProductDecorator {
        public TestDecorator(Sellable decoratedProduct) {
            super(decoratedProduct);
        }
    }
}
