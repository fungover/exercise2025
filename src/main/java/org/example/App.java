package org.example;

import org.example.Repository.InMemoryProductRepository;
import org.example.Repository.ProductRepository;
import org.example.entities.Category;
import org.example.entities.Product;
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
                .price(9.99)
                .build();

        productService.addProduct(p1);
        productService.addProduct(p2);

        Product retrieved = productService.getProductById("p001");
        System.out.println("Retrieved product: " + retrieved);

        Product updated = productService.updateProduct("p002", "Clue", Category.BOARDGAMES, 9);
        System.out.println("Updated product: " + updated);

        List<Product> allProducts = productService.getAllProducts();
        System.out.println("All products:");
        allProducts.forEach(System.out::println);

        List<Product> toys = productService.getProductsByCategorySorted(Category.TOYS);
        System.out.println("Toys category products:");
        toys.forEach(System.out::println);
    }
}
