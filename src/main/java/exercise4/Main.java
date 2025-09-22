package exercise4;

import exercise4.repository.InMemoryProductRepository;
import exercise4.repository.ProductRepository;
import exercise4.service.ProductServices;

public class Main {
    public static void main(String[] args) {
        ProductRepository productRepository = new InMemoryProductRepository();
        ProductServices productServices = new ProductServices(productRepository);
    }
}
