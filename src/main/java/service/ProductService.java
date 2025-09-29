package service;

import entities.Category;
import entities.Product;
import repository.ProductRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        if (productRepository == null) {
            throw new IllegalArgumentException("ProductRepository cannot be null");
        }
        this.productRepository = productRepository;
    }

    public void addProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        if (productRepository.getProductById(product.id()).isPresent()) {
            throw new IllegalArgumentException("Product with ID " + product.id() + " already exists");
        }
        productRepository.addProduct(product);
    }

    public void addNewProduct(String id, String name, Category category, int rating, double price) {
        Product product = new Product.Builder()
                .id(id)
                .name(name)
                .category(category)
                .rating(rating)
                .price(price)
                .asNewProduct()
                .build();
        addProduct(product);
    }

    public void updateProduct(String id, String name, Category category, int rating, double price) {
        Optional<Product> existingProduct = productRepository.findById(id);
        if (existingProduct.isEmpty()) {
            throw new IllegalArgumentException("Product with ID " + id + " not found");
        }

        Product original = existingProduct.get();
        Product updatedProduct = new Product.Builder()
                .id(id)
                .name(name)
                .category(category)
                .rating(rating)
                .price(price)
                .createdDate(original.createdDate())
                .modifiedDate(LocalDateTime.now())
                .build();

        productRepository.save(updatedProduct);
    }

    public List<Product> getAllProducts() {
        return productRepository.getAllProducts();
    }

    public Optional<Product> getProductById(String id) {
        return productRepository.getProductById(id);
    }

    public List<Product> getProductsByCategorySorted(Category category) {
        return productRepository.getAllProducts().stream()
                .filter(product -> product.category() == category)
                .sorted(Comparator.comparing(Product::name))
                .collect(Collectors.toList());
    }

    public List<Product> getProductsCreatedAfter(LocalDate date) {
        return productRepository.getAllProducts().stream()
                .filter(product -> product.createdDate().toLocalDate().isAfter(date))
                .collect(Collectors.toList());
    }

    public List<Product> getModifiedProducts() {
        return productRepository.getAllProducts().stream()
                .filter(Product::isModified)
                .collect(Collectors.toList());
    }

    public Set<Category> getCategoriesWithProducts() {
        return productRepository.getAllProducts().stream()
                .map(Product::category)
                .collect(Collectors.toSet());
    }

    public long countProductsInCategory(Category category) {
        if (category == null) {
            throw new IllegalArgumentException("Category cannot be null");
        }
        return productRepository.getAllProducts().stream()
                .filter(product -> product.category() == category)
                .count();
    }

    public Map<Character, Integer> getProductInitialsMap() {
        return productRepository.getAllProducts().stream()
                .map(product -> Character.toUpperCase(product.name().charAt(0)))
                .collect(Collectors.groupingBy(
                        initial -> initial,
                        Collectors.summingInt(e -> 1)
                ));
    }

    public List<Product> getTopRatedProductsThisMonth() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfMonth = now.withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0).withNano(0);

        Map<Integer, List<Product>> productsByRating = productRepository.getAllProducts().stream()
                .filter(product -> !product.createdDate().isBefore(startOfMonth))
                .collect(Collectors.groupingBy(Product::rating));

        if (productsByRating.isEmpty()) {
            return new ArrayList<>();
        }

        int maxRating = Collections.max(productsByRating.keySet());

        return productsByRating.get(maxRating).stream()
                .sorted(Comparator.comparing(Product::createdDate).reversed())
                .collect(Collectors.toList());
    }

    public List<Product> getHighQualityProducts(int minRating) {
        return productRepository.getAllProducts().stream()
                .filter(product -> product.rating() >= minRating)
                .sorted(Comparator.comparing(Product::rating).reversed())
                .collect(Collectors.toList());
    }

    public boolean hasProductsInCategory(Category category) {
        return productRepository.getAllProducts().stream()
                .anyMatch(product -> product.category() == category);
    }
}