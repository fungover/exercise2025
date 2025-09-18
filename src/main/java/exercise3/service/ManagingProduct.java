package exercise3.service;

import exercise3.entities.Category;
import exercise3.entities.Product;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class ManagingProduct implements Warehouse {

    private final List<Product> listOfProducts = new ArrayList<>();

    @Override
    public void addProduct(Product product) {

        Objects.requireNonNull(product, "Product cannot be null");

        boolean exists = listOfProducts.stream()
                        .anyMatch(item -> item.getId().equals(product.getId()));

        if(exists) {
            throw new IllegalArgumentException("Product already exists");
        }

        listOfProducts.add(product);
    }

    @Override
    public void updateProduct(String id, String name, Category category, int rating) {

        Product itemToUpdate = getProductById(id);
        listOfProducts.add(itemToUpdate.updateProduct(id, name, category, rating));
    }

    public List<Product> getAllProducts() {

        checkIsListIsEmpty();

        return Collections.unmodifiableList(listOfProducts);
    }

    @Override
    public Product getProductById(String id) {

        Objects.requireNonNull(id, "Product id cannot be null");

        Product productById = listOfProducts.stream()
                .filter(item -> item.getId().equals(id.trim()))
                .max(Comparator.comparing(Product::getModifiedDate))
                .orElse(null);

        if(productById == null){
            throw new IllegalArgumentException("Product doesn't exist");
        }

        return productById;
    }

    @Override
    public List<Product> getProductsByCategorySorted(Category category) {

        checkIsListIsEmpty();
        Objects.requireNonNull(category, "Category cannot be null");

        return listOfProducts.stream()
                .filter(item -> item.getCategory().equals(category))
                .sorted(Comparator.comparing(Product::getName)).toList();
    }

    @Override
    public List<Product> getProductsCreatedAfter(LocalDate after) {

        checkIsListIsEmpty();
        Objects.requireNonNull(after, "Date cannot be null");

        return listOfProducts.stream()
                .filter(item -> item.getCreatedDate().isAfter(after)).toList();

    }

    @Override
    public List<Product> getModifiedProducts() {

        checkIsListIsEmpty();

               return listOfProducts.stream()
                .filter(item -> !item.getCreatedDate().equals(item.getModifiedDate())).toList();

    }

    @Override
    public List<Category> getCategoriesWithProducts() {

        checkIsListIsEmpty();

        var countedProducts = listOfProducts.stream()
                .collect(Collectors.groupingBy(Product::getCategory, Collectors.counting()));

        return countedProducts.entrySet().stream()
                .filter(entry -> entry.getValue() > 0)
                .sorted(Map.Entry.comparingByKey())
                .map(Map.Entry::getKey).toList();

    }

    @Override
    public Long countProductsInCategory(Category category) {

        checkIsListIsEmpty();
        Objects.requireNonNull(category, "Category cannot be null");

        return listOfProducts.stream()
                .filter(item -> item.getCategory().equals(category)).count();
    }

    @Override
    public Map<Character, Integer> getProductsInitialsMap() {

        checkIsListIsEmpty();

        return listOfProducts.stream()
                .collect(Collectors.groupingBy(item -> item.getCategory().name().charAt(0),Collectors.collectingAndThen(Collectors.counting(), Long::intValue)));
    }

    @Override
    public List<Product> getTopProductsThisMonth() {
        LocalDate today = LocalDate.now();
        List<Product> listFilteredByMonth = listOfProducts.stream()
                .filter(item -> item.getCreatedDate().getYear() == today.getYear() &&
                        item.getCreatedDate().getMonth().equals(today.getMonth())).toList();

        return listFilteredByMonth.stream()
                .filter(item -> item.getRating() == 10)
                .sorted(Comparator.comparing(Product::getCreatedDate)).toList();
    }

    private void checkIsListIsEmpty() {
        if (listOfProducts.isEmpty()) {
            throw new IllegalArgumentException("Product list is empty");
        }
    }


}
