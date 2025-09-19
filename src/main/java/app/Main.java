package app;

import entities.Category;
import entities.Product;
import repository.InMemoryProductRepository;
import repository.ProductRepository;
import service.ProductService;
import java.time.LocalDate;

/**
 * Main is like a demo program, that is implementing a repository pattern with a service layer
 **/
public class Main {
    public static void main(String[] args) {

        //create repo, this is where everything gets stored
        ProductRepository repo = new InMemoryProductRepository();

        //inject repo in service
        ProductService service = new ProductService(repo);

        //create products with builder
        Product coffee = new Product.Builder()
                .id("A-001")
                .name("Coffee")
                .category(Category.FOOD)
                .rating(8)
                .createdDate(LocalDate.now())
                .modifiedDate(LocalDate.now())
                .build();

        Product lego = new Product.Builder()
                .id("B-001")
                .name("Lego")
                .category(Category.TOYS)
                .rating(8)
                .createdDate(LocalDate.now())
                .modifiedDate(LocalDate.now())
                .build();

        //add products
        service.addProduct(coffee);
        service.addProduct(lego);

        //use service
        System.out.println("All products");
        service.getAllProducts().forEach(System.out::println);

        System.out.println("Get one product by ID:");
        System.out.println(service.getProductById("A-001"));

        System.out.println("Sorted toys:");
        service.getProductsByCategorySorted(Category.TOYS).forEach(System.out::println);
    }
}

