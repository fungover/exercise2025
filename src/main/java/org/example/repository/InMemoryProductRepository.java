package org.example.repository;

import org.example.entities.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InMemoryProductRepository implements ProductRepository {

    // Här sparar vi alla produkter i en lista (simulerar en databas i minnet)
    private final List<Product> products = new ArrayList<>();

    @Override
    // Lägger till en ny produkt i listan
    public void addProduct(Product product) {
        products.add(product);
    }

    @Override
    // Försöker hämta en produkt med ett specifikt id
    // Returnerar Optional.empty() om produkten inte finns
    public Optional<Product> getProductById(String id) {
        return products.stream()
                .filter(p -> p.id().equals(id))
                .findFirst();
    }

    @Override
    // Returnerar en kopia av listan så att den riktiga listan inte kan ändras utifrån
    public List<Product> getAllProducts() {
        return new ArrayList<>(products);
    }

    @Override
    // Uppdaterar en produkt:
    // Tar först bort produkten med samma id och lägger sedan till den nya
    public void updateProduct(Product product) {
        products.removeIf(p -> p.id().equals(product.id()));
        products.add(product);
    }

    @Override
    // Tar bort en produkt med ett visst id (om den finns)
    public void deleteProduct(Product product) {
        products.removeIf(p -> p.id().equals(product.id()));
    }
}
