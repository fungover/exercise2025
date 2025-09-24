package repository;

import entities.Product;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * In-memory implementering av ProductRepository.
 * Denna klass ansvarar ENDAST för att lagra och hämta produkter.
 * Ingen affärslogik ska finnas här!
 */
public class InMemoryProductRepository implements ProductRepository {

    // Thread-safe Map för att lagra produkter
    private final Map<String, Product> products = new ConcurrentHashMap<>();

    @Override
    public void addProduct(Product product) {
        // Grundläggande validering
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        if (product.id() == null || product.id().isBlank()) {
            throw new IllegalArgumentException("Product ID cannot be null or blank");
        }

        // Kontrollera om produkten redan finns
        if (products.containsKey(product.id())) {
            throw new IllegalArgumentException("Product with ID " + product.id() + " already exists");
        }

        // Lagra produkten
        products.put(product.id(), product);
    }

    @Override
    public Optional<Product> getProductById(String id) {
        if (id == null || id.isBlank()) {
            return Optional.empty();
        }
        return Optional.ofNullable(products.get(id));
    }

    @Override
    public List<Product> getAllProducts() {
        // Returnerar en ny lista för att undvika externa modifieringar
        return products.values()
                .stream()
                .collect(Collectors.toList());
    }

    @Override
    public void updateProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        if (product.id() == null || product.id().isBlank()) {
            throw new IllegalArgumentException("Product ID cannot be null or blank");
        }

        // Kontrollera att produkten finns
        if (!products.containsKey(product.id())) {
            throw new IllegalArgumentException("Product with ID " + product.id() + " not found");
        }

        // Uppdatera produkten
        products.put(product.id(), product);
    }

    @Override
    public boolean removeProduct(String id) {
        if (id == null || id.isBlank()) {
            return false;
        }
        return products.remove(id) != null;
    }

    @Override
    public boolean existsById(String id) {
        if (id == null || id.isBlank()) {
            return false;
        }
        return products.containsKey(id);
    }

    @Override
    public long count() {
        return products.size();
    }

    /**
     * Hjälpmetod för debugging/testing
     * Rensar alla produkter från repository
     */
    public void clear() {
        products.clear();
    }

    /**
     * Hjälpmetod för debugging - visar alla lagrade ID:n
     */
    public Set<String> getAllIds() {
        return new HashSet<>(products.keySet());
    }
}