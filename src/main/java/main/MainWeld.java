package main;

import com.example.di.service.ProductService;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;

/** This starts Weld, searches for all my @ApplicationScoped classes,
 * injects dependencies with @Inject
 * and returns a finished ProductService
 * **/

public class MainWeld {
    public static void main(String[] args) {
        try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {
            ProductService service = container.select(ProductService.class).get();
            service.printAll();
        }
    }
}
