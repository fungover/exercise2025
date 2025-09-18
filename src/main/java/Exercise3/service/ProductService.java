package exercise3.service;

import exercise3.entities.Category;
import exercise3.entities.Product;
import exercise3.repository.ProductRepository;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getProductsByCategorySorted(Category category) {

        Objects.requireNonNull(category, "Category cannot be null");

        return productRepository.getAllProducts().stream()
                .filter(item -> item.getCategory().equals(category))
                .sorted(Comparator.comparing(Product::getName)).toList();
    }

    public List<Product> getProductsCreatedAfter(LocalDate after) {

        Objects.requireNonNull(after, "Date cannot be null");

        return productRepository.getAllProducts().stream()
                .filter(item -> item.getCreatedDate().isAfter(after)).toList();

    }


    public List<Product> getModifiedProducts() {

        return productRepository.getAllProducts().stream()
                .filter(item -> !item.getCreatedDate().equals(item.getModifiedDate())).toList();

    }

    public List<Category> getCategoriesWithProducts() {

        var countedProducts = productRepository.getAllProducts().stream()
                .collect(Collectors.groupingBy(Product::getCategory, Collectors.counting()));

        return countedProducts.entrySet().stream()
                .filter(entry -> entry.getValue() > 0)
                .sorted(Map.Entry.comparingByKey())
                .map(Map.Entry::getKey).toList();

    }

    public Long countProductsInCategory(Category category) {

        Objects.requireNonNull(category, "Category cannot be null");

        return productRepository.getAllProducts().stream()
                .filter(item -> item.getCategory().equals(category)).count();
    }

    public Map<Character, Integer> getProductsInitialsMap() {

        return productRepository.getAllProducts().stream()
                .collect(Collectors.groupingBy(item -> item.getCategory().name().charAt(0),Collectors.collectingAndThen(Collectors.counting(), Long::intValue)));
    }

    public List<Product> getTopProductsThisMonth() {
        LocalDate today = LocalDate.now();
        List<Product> listFilteredByMonth = productRepository.getAllProducts().stream()
                .filter(item -> item.getCreatedDate().getYear() == today.getYear() &&
                        item.getCreatedDate().getMonth().equals(today.getMonth())).toList();

        return listFilteredByMonth.stream()
                .filter(item -> item.getRating() == 10)
                .sorted(Comparator.comparing(Product::getCreatedDate)).toList();
    }


}
