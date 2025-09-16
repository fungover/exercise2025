package org.example.entities;

import org.example.repository.InMemoryProductRepository;
import org.example.repository.ProductRepository;
import org.example.service.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class DiscountDecoratorTest {
    @Test
    @DisplayName("Discount test")
    void getDiscountPriceTest() {
        ProductRepository repository = new InMemoryProductRepository();
        ProductService productService = new ProductService(repository);

        Product product1 = new Product.Builder()
                .id("1")
                .name("Laptop")
                .category(Category.ELECTRONICS)
                .rating(9)
                .createdDate(LocalDate.now())
                .modifiedDate(LocalDate.now())
                .price(1200.0)
                .build();

        productService.addProduct(product1);

        Product laptop = productService.getProductByID("1").orElseThrow(() -> new IllegalArgumentException("Product with id 1 not found")); ; // Original price is 1000.0
        Sellable discountedLaptop = new DiscountDecorator(laptop, 20);

        assertThat(product1.getPrice()).isEqualTo(1200.0);
        assertThat(discountedLaptop.getPrice()).isEqualTo(960.0);
    }
}