package repository;

import entities.Product;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Product data access operations.
 * Definierar kontraktet för hur vi lagrar och hämtar produkter.
 */
public interface ProductRepository {

    /**
     * Lägger till en ny produkt i repository
     * @param product Produkten som ska läggas till
     * @throws IllegalArgumentException om produkt med samma ID redan finns
     */
    void addProduct(Product product);

    /**
     * Hämtar en produkt baserat på dess ID
     * @param id Produkt-ID som ska sökas efter
     * @return Optional som innehåller produkten om den hittas, tom annars
     */
    Optional<Product> getProductById(String id);

    /**
     * Hämtar alla produkter i repository
     * @return Lista med alla produkter
     */
    List<Product> getAllProducts();

    /**
     * Uppdaterar en befintlig produkt
     * @param product Den uppdaterade produkten (måste ha befintligt ID)
     * @throws IllegalArgumentException om produkten inte finns
     */
    void updateProduct(Product product);

    /**
     * Tar bort en produkt baserat på ID
     * @param id ID på produkten som ska tas bort
     * @return true om produkten togs bort, false om den inte hittades
     */
    boolean removeProduct(String id);

    /**
     * Kontrollerar om en produkt med givet ID finns
     * @param id ID som ska kontrolleras
     * @return true om produkten finns, false annars
     */
    boolean existsById(String id);

    /**
     * Hämtar totalt antal produkter
     * @return Antal produkter i repository
     */
    long count();
}