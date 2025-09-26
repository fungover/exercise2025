package main;

import com.example.di.di.MiniContainer;
import com.example.di.repository.InMemoryProductRepository;
import com.example.di.repository.ProductRepository;
import com.example.di.service.ProductService;

public class MainContainer {
    public static void main(String[] args) {
        MiniContainer c = new MiniContainer();

        c.register(ProductRepository.class, InMemoryProductRepository.class);

        ProductService service = c.get(ProductService.class);
        service.printAll();
    }
}
