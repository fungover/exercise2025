package main;

import com.example.di.repository.InMemoryProductRepository;
import com.example.di.repository.ProductRepository;
import com.example.di.service.ProductServiceInterface;
import com.example.di.service.UppercaseProductService;

/**
 * Main
 **/
public class MainManual {
    public static void main(String[] args) {
        ProductRepository repo = new InMemoryProductRepository(); //OR DB
        ProductServiceInterface service = new UppercaseProductService(repo);
        service.printAll();
    }
}
