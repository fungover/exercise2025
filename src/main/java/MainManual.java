import com.example.di.InMemoryProductRepository;
import com.example.di.ProductRepository;
import com.example.di.ProductService;

/** Main **/
public class MainManual {
    public static void main(String[] args) {
        ProductRepository repo = new InMemoryProductRepository();
        ProductService service = new ProductService(repo);
        service.printAll();
    }
}
