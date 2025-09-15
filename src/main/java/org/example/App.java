package org.example;

import org.example.entities.Category;
import org.example.entities.Product;
import org.example.service.Warehouse;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main(String[] args) {
        Warehouse warehouse = new Warehouse();
        var products = getWarehouseProducts();
        products.forEach(product -> {
            warehouse.addProduct(product);
        });


//        iSeeBlackSheep(warehouse);

//        warehouse.updateProduct("1", "blueLaptop", Category.FOOD, 3);

        System.out.println();
        iSeeBlackSheep(warehouse);

        System.out.println("-".repeat(20));
//        warehouse.getProductsByCategorySorted(Category.FOOD)
//                 .forEach(System.out::println);

//        System.out.println(products.get(0)
        LocalDate now = LocalDate.now();
        System.out.println(warehouse.getProductsCreatedAfter(now));
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

    private static void iSeeBlackSheep(Warehouse warehouse) {
        warehouse.getAllProducts()
                 .stream()
                 .forEach(System.out::println);
    }
}
