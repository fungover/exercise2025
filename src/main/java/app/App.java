package app;

import app.entities.Category;
import app.entities.Product;
import app.repository.InMemoryProductRepository;
import app.service.ProductService;

public class App {
    public static void main(String[] args) {

        InMemoryProductRepository repository = new InMemoryProductRepository();

        ProductService productService = new ProductService(repository);

        productService.addProduct(new Product.Builder().id(0)
                .name("Bread")
                .category(Category.FOOD)
                .rating(5)
                .build()
        );

        productService.addProduct(new Product.Builder()
                .id(1)
                .name("Milk")
                .category(Category.FOOD)
                .rating(7)
                .build()
        );

        productService.getAllProducts().forEach(System.out::println);
    }
}
