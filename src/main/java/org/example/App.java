package org.example;

import org.example.entities.Category;
import org.example.entities.Product;
import org.example.repository.ProductRepository;
import org.example.repository.InMemoryProductRepository;
import org.example.service.ProductService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main(String[] args) {
        // create repository
        ProductRepository repository = new InMemoryProductRepository();
        //create service with repository
        ProductService productService = new ProductService(repository);
        var products = getWarehouseProducts();
        products.forEach(product -> {
            productService.addProduct(product);
        });


//        iSeeBlackSheep(productService);

//        productService.updateProduct("1", "blueLaptop", Category.FOOD, 3);

        System.out.println();
        iSeeBlackSheep(productService);

        System.out.println("-".repeat(20));
//        productService.getProductsByCategorySorted(Category.FOOD)
//                 .forEach(System.out::println);

//        System.out.println(products.get(0)
        LocalDate now = LocalDate.now();
        System.out.println(productService.getProductsCreatedAfter(now));
    }

    private static List<Product> getWarehouseProducts() {
        List<Product> products = new ArrayList<>();
        LocalDate yesterday = LocalDate.now()
                                       .plusDays(4);

        products.add(new Product.Builder().name("builder")
                                          .category(Category.GAMES)
                                          .rating(4)
                                          .build());

        products.add(new Product.Builder().name("laptop")
                                          .category(Category.ELECTRONICS)
                                          .rating(4)
                                          .createdDate(yesterday)
                                          .build());

        products.add(new Product.Builder().name("SlaughterBoat")
                                          .category(Category.GAMES)
                                          .rating(7)
                                          .build());

        products.add(new Product.Builder().name("Cake")
                                          .category(Category.FOOD)
                                          .rating(10)
                                          .build());

        products.add(new Product.Builder().name("DuckDetective")
                                          .category(Category.GAMES)
                                          .rating(8)
                                          .createdDate(yesterday)
                                          .build());

        products.add(new Product.Builder().name("lamp")
                                          .category(Category.ELECTRONICS)
                                          .rating(10)
                                          .build());

        products.add(new Product.Builder().name("PC")
                                          .category(Category.ELECTRONICS)
                                          .rating(4)
                                          .build());

        products.add(new Product.Builder().name("DogFood")
                                          .category(Category.FOOD)
                                          .rating(2)
                                          .build());

        products.add(new Product.Builder().name("CatFood")
                                          .category(Category.FOOD)
                                          .rating(7)
                                          .build());

        return products;
    }

    private static void iSeeBlackSheep(ProductService productService) {
        productService.getAllProducts()
                      .stream()
                      .forEach(System.out::println);
    }
}
