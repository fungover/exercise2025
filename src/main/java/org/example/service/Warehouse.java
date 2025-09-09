package org.example.service;

import org.example.entities.Product;
import org.example.entities.Category;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Warehouse {
    private final List<Product> products = new ArrayList<>();

    // Lägger till en produkt i lagret (kontrollerar null och dubblett-id)
    public void addProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("product cannot be null");
        }

        boolean idExists = products.stream()
                .anyMatch(p -> p.id().equals(product.id()));
        if (idExists) {
            throw new IllegalArgumentException("product with id " + product.id() + " already exists");
        }

        products.add(product);
    }
    // Returnerar en kopia av lagret för att skydda intern lista
    public List<Product> getAllProducts() {
        return new ArrayList<>(products);
    }

    // Returnerar en produkt med specifikt id (eller kastar exception om inte hittad)
    public Product getProductById(String id) {
        return products.stream()
                .filter(p -> p.id().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("id not found: " + id));
    }

    // Returnerar en lista med produkter i en specifik kategori sorterade efter namn
    public List<Product> getProductsByCategorySorted(Category category) {
        return products.stream()
                .filter(p -> p.category() == category)
                .sorted(Comparator.comparing(Product::name))
                .toList();
    }

    // Returnerar en lista med produkter skapade efter en specifik datum
    public List<Product> getProductsCreatedAfter(LocalDate date) {
        return products.stream()
                .filter(p -> p.createdDate().isAfter(date))
                .toList();
    }

    // Returnerar en lista med produkter som har modifierats (dvs modifiedDate != createdDate)
    public List<Product> getModifiedProducts() {
        return products.stream()
                .filter(p -> !p.modifiedDate().equals(p.createdDate()))
                .toList();
    }

    // Returnerar en lista med alla kategorier med produkter (unika värden)
    public List<Category> getCategoriesWithProducts() {
        return products.stream()
                .map(Product::category)
                .distinct()
                .toList();
    }

    // Returnerar antalet produkter i en specifik kategori
    public int countProductsInCategory(Category category) {
        return (int) products.stream()
                .filter(p -> p.category() == category)
                .count();
    }

    // Bygger en karta (Map) med initialer som nycklar
    // och hur många produktnamn som börjar på varje bokstav
    public Map<Character, Integer> getProductInitialsMap() {
        return products.stream()
                .map(p -> p.name().trim())
                .filter(s -> !s.isEmpty())
                .map (s -> Character.toUpperCase(s.charAt(0)))
                .collect(Collectors.groupingBy(c -> c, Collectors.summingInt(x -> 1)));
    }

    // Returnerar produkter med maxbetyg (10) som är skapade samma månad som 'now'
    // Resultatet sorteras efter createdDate, med nyaste produkter först
    public List<Product> getTopRatedProductsThisMonth(LocalDate now) {
        return products.stream()
                .filter(p -> p.rating() == 10)
                .filter(p -> p.createdDate().getYear() == now.getYear()
                        && p.createdDate().getMonth() == now.getMonth())
                .sorted(Comparator.comparing(Product::createdDate).reversed())
                .toList();
    }
}
