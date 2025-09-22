package exercise4.service;

import exercise4.entities.Category;
import exercise4.entities.Product;
import exercise4.repository.ProductRepository;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;


public class ProductServices {
   // private final List<Product> products;
    private final ProductRepository productRepository;

    public ProductServices(ProductRepository productRepository) {
        this.productRepository = productRepository;
       // products = new ArrayList<>();
    }
    public void addProduct(Product product) {
        productRepository.addProduct(product);
    }
    public void updateProduct(Product product) {

        productRepository.updateProduct(product);

    }
    public List<Product> getAllProducts() {
        return productRepository.getAllProducts();
    }
    public Optional<Product> getProductById(String id){
        return productRepository.getProductById(id);
    }

    public List<Product> getProductsByCategorySorted(Category category){
        List<Product> products = productRepository.getAllProducts();
        List<Product> productsInCategory = products.stream().
                filter(product -> product.getCategory() == category).
                sorted(Comparator.comparing(Product::getName, String::compareToIgnoreCase)).
                toList();
        return List.copyOf(productsInCategory);
    }

    public List<Product> getProductsCreatedAfter(LocalDateTime date){
        List<Product> products = productRepository.getAllProducts();
        if(date == null){
            throw new IllegalArgumentException("Date cannot be null");
        }
        List<Product> productsCreatedAfter = products.stream()
                .filter(param -> param.getCreatedDate().isAfter(date))
                .toList();
        return List.copyOf(productsCreatedAfter);
    }

    public List<Product> getModifiedProducts(){
        List<Product> products = productRepository.getAllProducts();
        List<Product> modifiedProducts = products.stream().
                filter(param -> param.getModifiedDate()
                        .isAfter(param.getCreatedDate()))
                .toList();
        return List.copyOf(modifiedProducts);
    }
}
