package org.example.repository;

import org.example.entities.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    // Spara en produkt
    void addProduct(Product product);

    // Hämta en produkt om den finns därav optional
    Optional<Product> getProductById(String id);

    // Hämta alla produkter (repository bestämmer hur de lagras)
    List<Product> getAllProducts();

    // Uppdatera befintlig produkt
    void updateProduct(Product product);

    // Ta bort en produkt
    void deleteProduct(Product product);
}
