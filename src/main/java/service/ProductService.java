package service;

import entities.Category;
import entities.Product;
import repository.ProductRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * ProductService - Business logic layer that uses repository for data access
 * Ansvarar för affärslogik och validering, medan repository hanterar datalagring
 */
public class ProductService {

    private final ProductRepository productRepository;

    /**
     * Constructor injection av repository
     */
    public ProductService(ProductRepository productRepository) {
        this.productRepository = Objects.requireNonNull(productRepository,
                "ProductRepository cannot be null");
    }

    /**
     * Lägger till en ny produkt
     */
    public void addProduct(Product product) {
        // Validering (business logic)
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }

        if (product.id() == null || product.id().isBlank()) {
            throw new IllegalArgumentException("Product id cannot be null or blank");
        }

        // Kontrollera om produkten redan finns genom repository
        if (productRepository.getProductById(product.id()).isPresent()) {
            throw new IllegalArgumentException("Product with ID " + product.id() + " already exists");
        }

        // Delegera till repository för datalagring
        productRepository.addProduct(product);
    }

    /**
     * Bekvämlighetsmetod för att lägga till ny produkt med Builder pattern
     */
    public void addNewProduct(String id, String name, Category category, int rating) {
        Product product = new Product.Builder()
                .id(id)
                .name(name)
                .category(category)
                .rating(rating)
                .asNewProduct()  // Sätter current timestamps
                .build();

        addProduct(product);
    }

    /**
     * Uppdaterar en befintlig produkt
     */
    public void updateProduct(String id, String name, Category category, int rating) {
        // Validering
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("id cannot be null or blank");
        }
        if (category == null) {
            throw new IllegalArgumentException("category cannot be null");
        }

        // Hitta befintlig produkt genom repository
        Product existingProduct = productRepository.getProductById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product with ID " + id + " not found"));

        // Skapa uppdaterad produkt med Builder
        Product updatedProduct = new Product.Builder()
                .id(existingProduct.id())
                .name(name)
                .category(category)
                .rating(rating)
                .createdDate(existingProduct.createdDate())  // Behåll ursprungligt datum
                .modifiedDate(LocalDateTime.now())           // Uppdatera modified datum
                .build();

        // Uppdatera genom repository
        productRepository.updateProduct(updatedProduct);
    }

    /**
     * Hämtar alla produkter - delegerar direkt till repository
     */
    public List<Product> getAllProducts() {
        return productRepository.getAllProducts();
    }

    /**
     * Hämtar en specifik produkt med ID - delegerar direkt till repository
     */
    public Optional<Product> getProductById(String id) {
        return productRepository.getProductById(id);
    }

    /**
     * Hämtar produkter i en specifik kategori, sorterade A-Z efter namn
     */
    public List<Product> getProductsByCategorySorted(Category category) {
        return productRepository.getAllProducts()
                .stream()
                .filter(product -> product.category().equals(category))
                .sorted(Comparator.comparing(Product::name))
                .collect(Collectors.toList());
    }

    /**
     * Hämtar produkter skapade efter ett specifikt datum
     */
    public List<Product> getProductsCreatedAfter(LocalDate date) {
        return productRepository.getAllProducts()
                .stream()
                .filter(product -> product.createdDate().toLocalDate().isAfter(date))
                .collect(Collectors.toList());
    }

    /**
     * Hämtar produkter som har blivit modifierade
     */
    public List<Product> getModifiedProducts() {
        return productRepository.getAllProducts()
                .stream()
                .filter(Product::isModified)
                .collect(Collectors.toList());
    }

    // ========== VG-METODER ==========

    /**
     * Hämtar alla kategorier som har minst en produkt
     */
    public Set<Category> getCategoriesWithProducts() {
        return productRepository.getAllProducts()
                .stream()
                .map(Product::category)
                .collect(Collectors.toSet());
    }

    /**
     * Räknar antal produkter i en specifik kategori
     */
    public long countProductsInCategory(Category category) {
        if (category == null) {
            throw new IllegalArgumentException("Category cannot be null");
        }

        return productRepository.getAllProducts()
                .stream()
                .filter(product -> product.category() == category)
                .count();
    }

    /**
     * Returnerar en Map med första bokstäver i produktnamn och deras antal
     */
    public Map<Character, Integer> getProductInitialsMap() {
        return productRepository.getAllProducts()
                .stream()
                .map(Product::name)
                .filter(name -> name != null && !name.isEmpty())
                .map(name -> name.toUpperCase().charAt(0))
                .collect(Collectors.groupingBy(
                        Function.identity(),
                        Collectors.counting()
                ))
                .entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().intValue()
                ));
    }

    /**
     * Produkter med högsta betyg skapade denna månad, sorterade nyaste först
     */
    public List<Product> getTopRatedProductsThisMonth() {
        YearMonth currentMonth = YearMonth.now();

        List<Product> thisMonthProducts = productRepository.getAllProducts()
                .stream()
                .filter(product -> YearMonth.from(product.createdDate()).equals(currentMonth))
                .collect(Collectors.toList());

        if (thisMonthProducts.isEmpty()) {
            return new ArrayList<>();
        }

        int maxRating = thisMonthProducts.stream()
                .mapToInt(Product::rating)
                .max()
                .orElse(0);

        return thisMonthProducts.stream()
                .filter(product -> product.rating() == maxRating)
                .sorted(Comparator.comparing(Product::createdDate).reversed())
                .collect(Collectors.toList());
    }

    /**
     * Hämtar produkter med betyg över angiven tröskel
     */
    public List<Product> getHighQualityProducts(int minRating) {
        if (minRating < 0 || minRating > 10) {
            throw new IllegalArgumentException("Rating must be between 0 and 10");
        }

        return productRepository.getAllProducts()
                .stream()
                .filter(product -> product.rating() >= minRating)
                .sorted(Comparator.comparing(Product::rating).reversed())
                .collect(Collectors.toList());
    }

    /**
     * Kontrollera om warehouse har produkter i given kategori
     */
    public boolean hasProductsInCategory(Category category) {
        return productRepository.getAllProducts()
                .stream()
                .anyMatch(product -> product.category().equals(category));
    }
}