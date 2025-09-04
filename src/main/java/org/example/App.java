package org.example;

import org.example.entities.Category;
import org.example.entities.Product;
import org.example.service.Warehouse;

import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main(String[] args) {
        Warehouse warehouse = new Warehouse();
        var products = getWarehouseProducts();
        products.forEach(product -> {
            warehouse.addProduct(product);
        });


        iSeeBlackSheep(warehouse);

        warehouse.updateProduct("1", "blueLaptop", Category.FOOD, 3);

        System.out.println();
        iSeeBlackSheep(warehouse);

    }

    private static List<Product> getWarehouseProducts() {
        List<Product> products = new ArrayList<>();

        products.add(new Product("laptop", Category.ELECTRONICS, 4));
        products.add(new Product("SlaughterBoat", Category.GAMES, 7));
        products.add(new Product("cake", Category.FOOD, 10));
        products.add(new Product("DuckDetective", Category.GAMES, 8));


        return products;
    }

    private static void iSeeBlackSheep(Warehouse warehouse) {
        warehouse.getAllProducts()
                 .stream()
                 .forEach(System.out::println);
    }
}
