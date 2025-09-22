package exersice4.service;

import exersice4.enteties.Category;
import exersice4.enteties.Product;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;


public class Warehouse {
    private final List<Product> products;

    public Warehouse(){
        products = new ArrayList<>();
    }
    public void addProduct(Product product) {
        if(product.getName() == null){
            System.out.println("Product could not be added");
            return;
        }
        products.add(product);
    }
    public void updateProduct(String id, String name, Category category, int rating, LocalDateTime createdDate) {
        Optional<Product> productToUpdate = products.stream().
                filter(product -> product.getId().equals(id)).
                findFirst();

        if(productToUpdate.isPresent()){
            int index = products.indexOf(productToUpdate.get());
            /*productToUpdate.ifPresent(product -> product.setName(name));
            productToUpdate.ifPresent(product -> product.setCategory(category));
            productToUpdate.ifPresent(product -> product.setRating(rating));
            productToUpdate.ifPresent(product -> product.setModifiedDate(LocalDateTime.now()));*/
            //Product updatedProduct = new Product(name, category, rating, createdDate, id);
            Product updatedProduct = new Product.Builder()
                    .setName(name)
                    .setCategory(category)
                    .setRating(rating)
                    .setCreatedDate(createdDate)
                    .setId(id)
                    .build();
            products.set(index, updatedProduct);


        }else{
            System.out.println("Product not found");
        }
    }
    public List<Product> getAllProducts() {
        return List.copyOf(products);
    }
    public Product getProductById(String id){
        Optional<Product> productToUpdate = products.stream()
                .filter(product -> product.getId().equals(id)).
                findFirst();
        return productToUpdate.orElse(null);
    }

    public List<Product> getProductsByCategorySorted(Category category){
        List<Product> productsInCategory = products.stream().
                filter(product -> product.getCategory() == category).
                sorted(Comparator.comparing(Product::getName, String::compareToIgnoreCase)).
                toList();
        return List.copyOf(productsInCategory);
    }

    public List<Product> getProductsCreatedAfter(LocalDateTime date){
        if(date == null){
            System.out.println("Date cannot be null");
            return null;
        }
        List<Product> productsCreatedAfter = products.stream()
                .filter(param -> param.getCreatedDate().isAfter(date))
                .toList();
        return List.copyOf(productsCreatedAfter);
    }

    public List<Product> getModifiedProducts(){
        List<Product> modifiedProducts = products.stream().
                filter(param -> param.getModifiedDate()
                        .isAfter(param.getCreatedDate()))
                .toList();
        return List.copyOf(modifiedProducts);
    }
}
