package org.example;

import org.example.decorator.DiscountDecorator;
import org.example.entities.Category;
import org.example.entities.Product;
import org.example.entities.Sellable;
import org.example.repository.InMemoryProductRepository;
import org.example.repository.ProductRepository;
import org.example.service.ProductService;

import java.math.BigDecimal;
import java.time.LocalDate;

public class App {
    public static void main(String[] args) {
        // Create repository and service
        ProductRepository productRepository = new InMemoryProductRepository();
        ProductService productService = new ProductService(productRepository);

        // Add a product (example: laptop)
        Product laptop = Product.builder()
                .id("laptop-1")
                .name("Gaming Laptop")
                .category(Category.ELECTRONICS)
                .rating(9)
                .price(new BigDecimal("1000.00"))
                .createdDate(LocalDate.now())
                .build();
        productService.addProduct(laptop);

        // Get product from service with id
        Product gaminglaptop = productService.getProductById("laptop-1").get();

        // Decorate it with a 20% discount
        Sellable discountedLaptop = new DiscountDecorator(gaminglaptop, 20);

        // Demonstrate the decorator pattern
        System.out.println("\n------ Original product ------");
        System.out.println("Name: " + gaminglaptop.getName() + "\nOriginal price: " + gaminglaptop.getPrice());
        System.out.println("\n------ Apply 20% discount ------");
        System.out.println("Name: " + gaminglaptop.getName() + "\nDiscounted price: " + discountedLaptop.getPrice());

        // Ability to stack decorators
        Sellable doubleDiscounted = new DiscountDecorator(discountedLaptop, 10);
        System.out.println("\n------ Apply 10% discount on top of the previous discount ------");
        System.out.println("Name: " + gaminglaptop.getName() + "\nDouble discounted price: " + doubleDiscounted.getPrice());
    }
}
