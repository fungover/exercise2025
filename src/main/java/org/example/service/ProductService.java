package org.example.service;

import org.example.entities.Product;
import org.example.entities.Category;
import org.example.repository.ProductRepository;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProductService {
    private final ProductRepository repository;

    // Service använder ett repository för att komma åt data.
    // Detta kallas Dependency Injection: vi skickar in ett repository i konstruktorn.
    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    // Lägger till en ny produkt
    // Kollar först att produkten inte är null och att id inte redan finns
    public void addProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("product cannot be null");
        }
        if (repository.getProductById(product.id()).isPresent()) {
            throw new IllegalArgumentException("id already exists");
        }

        repository.addProduct(product);
    }

    // Uppdaterar en befintlig produkt
    // Kollar att produkten inte är null och att id redan finns (annars kan man inte uppdatera)
    public void updateProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("product cannot be null");
        }
        if (repository.getProductById(product.id()).isEmpty()) {
            throw new IllegalArgumentException("cannot update nonexisting product with id: " + product.id());
        }

        repository.updateProduct(product);
    }

    // Tar bort en produkt
    // Kollar att produkten inte är null och att id finns innan vi försöker ta bort
    public void deleteProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("product cannot be null");
        }
        if (repository.getProductById(product.id()).isEmpty()) {
            throw new IllegalArgumentException("cannot delete nonexisting product with id: " + product.id());
        }

        repository.deleteProduct(product);
    }

    // Hämtar en produkt med specifikt id
    // Om id inte hittas kastas ett exception
    public Product getProductById(String id) {
        return repository.getProductById(id)
                .orElseThrow(() -> new IllegalArgumentException("id not found: " + id));
    }

    // Hämtar alla produkter från repository
    public List<Product> getAllProducts() {
        return repository.getAllProducts();
    }

    // Hämtar produkter från en kategori, sorterar dem i bokstavsordning
    public List<Product> getProductsByCategorySorted(Category category) {
        return repository.getAllProducts().stream()
                .filter(p -> p.category() == category)
                .sorted(Comparator.comparing(Product::name))
                .toList();
    }

    // Hämtar produkter som är skapade efter ett visst datum
    public List<Product> getProductsCreatedAfter(LocalDate date) {
        return repository.getAllProducts().stream()
                .filter(p -> p.createdDate().isAfter(date))
                .toList();
    }

    // Hämtar produkter som har ändrats efter att de skapades
    public List<Product> getModifiedProducts() {
        return repository.getAllProducts().stream()
                .filter(p -> !p.modifiedDate().equals(p.createdDate()))
                .toList();
    }

    // Hämtar alla kategorier där det finns produkter
    public List<Category> getCategoriesWithProducts() {
        return repository.getAllProducts().stream()
                .map(Product::category)
                .distinct()
                .toList();
    }

    // Räknar hur många produkter som finns i en viss kategori
    public int countProductsInCategory(Category category) {
        return (int) repository.getAllProducts().stream()
                .filter(p -> p.category() == category)
                .count();
    }

    // Skapar en karta med första bokstaven i produktnamnet
    // och räknar hur många produkter som börjar på varje bokstav
    public Map<Character, Integer> getProductInitialsMap() {
        return repository.getAllProducts().stream()
                .map(p -> p.name().trim())
                .filter(s -> !s.isEmpty())
                .map(s -> Character.toUpperCase(s.charAt(0)))
                .collect(Collectors.groupingBy(c -> c, Collectors.summingInt(x -> 1)));
    }

    // Hämtar alla produkter med högsta betyg (10)
    // men bara de som skapats samma månad som "now"
    // Sorteras så att nyaste produkterna kommer först
    public List<Product> getTopRatedProductsThisMonth(LocalDate now) {
        return repository.getAllProducts().stream()
                .filter(p -> p.rating() == 10)
                .filter(p -> p.createdDate().getYear() == now.getYear()
                        && p.createdDate().getMonth() == now.getMonth())
                .sorted(Comparator.comparing(Product::createdDate).reversed())
                .toList();
    }
}
