package exersice4;

import exersice4.repository.InMemoryProductRepository;
import exersice4.repository.ProductRepository;
import exersice4.service.ProductServices;

public class Main {
    public static void main(String[] args) {
        ProductRepository productRepository = new InMemoryProductRepository();
        ProductServices productServices = new ProductServices(productRepository);
    }
}
