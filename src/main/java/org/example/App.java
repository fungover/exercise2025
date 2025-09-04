package org.example;

import org.example.entities.Category;
import org.example.entities.Product;
import org.example.service.Warehouse;

import java.util.List;

public class App {
    public static void main(String[] args) {

        Warehouse warehouse = new Warehouse();

        warehouse.addProduct(Warehouse.createProduct("1", "Gaming Laptop", Category.ELECTRONICS, 9));
        warehouse.addProduct(Warehouse.createProduct("2", "Running Shoes", Category.SPORTS, 8));
        warehouse.addProduct(Warehouse.createProduct("3", "Java Book", Category.BOOKS, 7));
        warehouse.addProduct(Warehouse.createProduct("4", "Javascript Book", Category.BOOKS, 10));
        warehouse.addProduct(Warehouse.createProduct("5", "Gaming Mouse", Category.ELECTRONICS, 8));

        System.out.println("\n=== ELECTRONICS (SORTED BY NAME) ===");
        List<Product> electronics = warehouse.getProductsByCategorySorted(Category.BOOKS);
        electronics.forEach(System.out::println);
    }
}
