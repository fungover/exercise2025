package org.example;

import org.example.entities.Category;
import org.example.entities.DiscountDecorator;
import org.example.entities.Product;
import org.example.repository.InMemoryProductRepository;
import org.example.service.ProductService;

public class App {
    public static void main(String[] args) {
        InMemoryProductRepository inMemoryProductRepository = new InMemoryProductRepository();
        ProductService productService = new ProductService(inMemoryProductRepository);

        Product laptop = new Product.Builder()
                .id("123")
                .name("ASUS Gaming Laptop")
                .category(Category.ELECTRONICS)
                .rating(10)
                .price(1337)
                .build();

        DiscountDecorator discountedLaptop = new DiscountDecorator(laptop, 90);

        System.out.println("Original price: " + laptop.getPrice() + "kr");
        System.out.println(String.format("%.0f", discountedLaptop.getDiscountPercentage()) + "% off deal: " + String.format("%.2f", discountedLaptop.getPrice()) + "kr");

    }
}
