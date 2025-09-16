package org.example;

import org.example.Repository.InMemoryProductRepository;
import org.example.Repository.ProductRepository;
import org.example.entities.Category;
import org.example.entities.DiscountDecorator;
import org.example.entities.Product;
import org.example.entities.Sellable;
import org.example.service.ProductService;

import java.util.List;

public class App {
    public static void main(String[] args) {

        ProductRepository repository = new InMemoryProductRepository();

        ProductService productService = new ProductService(repository);

        Product p1 = new Product.Builder()
                .id("p001")
                .name("Car")
                .category(Category.TOYS)
                .rating(8)
                .price(19.99)
                .build();

        Product p2 = new Product.Builder()
                .id("p002")
                .name("Clue")
                .category(Category.BOARDGAMES)
                .rating(7)
                .price(39.99)
                .build();

        productService.addProduct(p1);
        productService.addProduct(p2);

        Sellable discountedCar = new DiscountDecorator(p2, 20);

        System.out.println("Original price of Clue: " + p2.getPrice());
        System.out.printf("Discounted price of Clue (20%% off): %.2f%n", discountedCar.getPrice());
    }
}
