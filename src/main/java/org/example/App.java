package org.example;

import org.example.entities.Category;
import org.example.entities.DiscountDecorator;
import org.example.entities.Product;
import org.example.entities.Sellable;
import org.example.repository.InMemoryProductRepository;
import org.example.repository.ProductRepository;
import org.example.service.ProductService;

import java.time.LocalDate;

public class App {
    public static void main(String[] args) {

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
        Product product2 = new Product.Builder()
                .id("2")
                .name("Laptop")
                .category(Category.TOYS)
                .rating(9)
                .createdDate(LocalDate.now())
                .modifiedDate(LocalDate.now())
                .price(1200.0)
                .build();
        productService.addProduct(product1);
        productService.addProduct(product2);

        /*System.out.println(productService.getAllProducts());*/
        System.out.println(productService.getProductsByCategorySorted(Category.ELECTRONICS));

        Product laptop = productService.getProductByID("1").orElseThrow(() -> new IllegalArgumentException("Product with id 1 not found")); ; // Original price is 1000.0

        Sellable discountedLaptop = new DiscountDecorator(laptop, 20);

        System.out.println(laptop);
        System.out.println(discountedLaptop.getPrice());



    }
}
