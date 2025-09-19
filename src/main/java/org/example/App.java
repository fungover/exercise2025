package org.example;

import org.example.decorators.DiscountDecorator;
import org.example.entities.Category;
import org.example.entities.Product;
import org.example.entities.Sellable;
import org.example.repository.InMemoryProductRepository;
import org.example.repository.ProductRepository;
import org.example.service.ProductService;

import java.time.LocalDate;

public class App {
    public static void main(String[] args) {
        // Create repository
        ProductRepository repo = new InMemoryProductRepository();
        //Create service and inject repository
        ProductService productService = new ProductService(repo);

        Product kiwi = new Product.Builder()
                .id("1")
                .name("Kiwi")
                .category(Category.Food)
                .rating(9)
                .price(10)
                .createdDate(LocalDate.now())
                .modifiedDate(LocalDate.now())
                .build();

        productService.addProduct(kiwi);

        Sellable discountedKiwi = new DiscountDecorator(kiwi, 2.5);

        System.out.println("Originalpris: " + kiwi.getPrice());
        System.out.println("Rabatterat pris: " + discountedKiwi.getPrice());
        System.out.println("Antal produkter: " + productService.getAllProducts().size());
    }
}
