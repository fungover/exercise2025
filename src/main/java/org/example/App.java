package org.example;


import org.example.entities.Category;
import org.example.entities.Product;
import org.example.repository.InMemoryProductRepository;
import org.example.repository.ProductRepository;
import org.example.service.ProductService;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class App {
    public static void main(String[] args) {

        ProductRepository repository = new InMemoryProductRepository();
        ProductService productService = new ProductService(repository);

        Product p1 = new Product.ProductBuilder()
                .id("1")
                .name("Harry Potter")
                .category(Category.BOOKS)
                .rating(9)
                .build();

        Product p2 = new Product.ProductBuilder()
                .id("2")
                .name("HOKA One One")
                .category(Category.SHOES)
                .rating(10)
                .build();

        Product apple = new Product.ProductBuilder()
                .id("10")
                .name("Apple")
                .category(Category.FOOD)
                .rating(5)
                .createdDate(LocalDateTime.now().minusDays(2))
                .modifiedDate(LocalDateTime.now().minusDays(2))
                .build();

        Product pizza = new Product.ProductBuilder()
                .id("11")
                .name("Pizza")
                .category(Category.FOOD)
                .rating(9)
                .createdDate(LocalDateTime.now().minusDays(1))
                .modifiedDate(LocalDateTime.now().minusDays(1))
                .build();

        Product tacos = new Product.ProductBuilder()
                .id("12")
                .name("Tacos")
                .category(Category.FOOD)
                .rating(7)
                .createdDate(LocalDateTime.now())
                .modifiedDate(LocalDateTime.now())
                .build();


        productService.addProduct(p1);
        productService.addProduct(p2);

        System.out.println("ALL PRODUCTS");
        productService.getAllProducts().forEach(System.out::println);
        System.out.println();

        System.out.println("GET PRODUCT BY ID");
        System.out.println(productService.getProductById("1"));
        System.out.println();

        System.out.println("UPDATE PRODUCT");
        productService.updateProduct("2", "HOKA One One Updated", Category.SHOES, 8);
        productService.getAllProducts().forEach(System.out::println);
        System.out.println();

        System.out.println("PRODUCTS IN CATEGORY SHOES");
        productService.getProductsByCategorySorted(Category.SHOES)
                .forEach(System.out::println);
        System.out.println();

        productService.addProduct(apple);
        productService.addProduct(pizza);
        productService.addProduct(tacos);

        System.out.println("PRODUCTS SORTED BY CATEGORY FOOD");
        productService.getProductsByCategorySorted(Category.FOOD)
                .forEach(System.out::println);
        System.out.println();

        System.out.println("PRODUCTS CREATED AFTER YESTERDAY");
        Product p3 = new Product.ProductBuilder()
                .id("3")
                .name("New Book")
                .category(Category.BOOKS)
                .rating(7)
                .createdDate(LocalDateTime.now().minusDays(1))
                .modifiedDate(LocalDateTime.now())
                .build();
        productService.addProduct(p3);

        productService.getProductsCreatedAfter(LocalDate.now().minusDays(1))
                .forEach(System.out::println);
        System.out.println();

        System.out.println("MODIFIED PRODUCTS");
        productService.getModifiedProducts().forEach(System.out::println);
    }


    }

