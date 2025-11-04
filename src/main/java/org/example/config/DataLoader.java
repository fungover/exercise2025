package org.example.config;

import org.example.model.Product;
import org.example.model.Location;
import org.example.repository.ProductRepository;
import org.example.repository.LocationRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initData(ProductRepository productRepo, LocationRepository locationRepo) {
        return args -> {
            Location loc = new Location();
            loc.setZone("A");
            loc.setShelf("3");
            locationRepo.save(loc);

            Product p = new Product();
            p.setName("Catalytic converter");
            p.setSku("CC-001");
            p.setQuantity(50);
            p.setLocation(loc);
            productRepo.save(p);

            List<Product> products = productRepo.findAll();
            products.forEach(prod -> {
                Location location = prod.getLocation();
                if (location != null) {
                    System.out.println(prod.getName() + " @ " + loc.getZone());
                } else {
                    System.out.println(prod.getName() + " has no location");
                }

            });
        };
    }
}