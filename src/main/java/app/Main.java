package app;

import entities.Category;
import entities.Product;
import entities.Sellable;
import decorators.DiscountDecorator;
import repository.InMemoryProductRepository;
import repository.ProductRepository;
import service.ProductService;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== DECORATOR PATTERN DEMONSTRATION ===\n");

        // Setup
        ProductRepository repository = new InMemoryProductRepository();
        ProductService productService = new ProductService(repository);

        // Add products
        productService.addNewProduct("LAPTOP001", "Gaming Laptop", Category.ELECTRONICS, 9, 10000.0);
        productService.addNewProduct("PHONE001", "iPhone 15", Category.ELECTRONICS, 10, 12000.0);
        productService.addNewProduct("BOOK001", "Design Patterns", Category.BOOKS, 9, 500.0);

        demonstrateBasicDiscount(productService);
        demonstrateMultipleDiscounts(productService);
        demonstrateDifferentCustomers(productService);
    }

    private static void demonstrateBasicDiscount(ProductService productService) {
        System.out.println("1. GRUNDLÄGGANDE RABATT");
        System.out.println("=======================");

        // Download product from repository
        Product laptop = productService.getProductById("LAPTOP001").orElseThrow();

        System.out.println("Original produkt:");
        System.out.println("- Namn: " + laptop.getName());
        System.out.println("- Pris: " + laptop.getPrice() + " kr");
        System.out.println();

        // Apply 20% discount with Decorator
        Sellable discountedLaptop = new DiscountDecorator(laptop, 20);

        System.out.println("Med 20% rabatt:");
        System.out.println("- Namn: " + discountedLaptop.getName());
        System.out.println("- Pris: " + discountedLaptop.getPrice() + " kr");
        System.out.println("- Beskrivning: " + discountedLaptop.getDescription());
        System.out.println("- Du sparar: " + ((DiscountDecorator) discountedLaptop).getSavings() + " kr");
        System.out.println();
    }

    private static void demonstrateMultipleDiscounts(ProductService productService) {
        System.out.println("2. KEDJA AV RABATTER");
        System.out.println("====================");

        Product phone = productService.getProductById("PHONE001").orElseThrow();

        System.out.println("Original pris: " + phone.getPrice() + " kr");

        // Discount one
        Sellable firstDiscount = new DiscountDecorator(phone, 10);
        System.out.println("Efter 10% rabatt: " + firstDiscount.getPrice() + " kr");

        // Discount two (on the already discounted price)
        Sellable secondDiscount = new DiscountDecorator(firstDiscount, 5);
        System.out.println("Efter ytterligare 5% rabatt: " + secondDiscount.getPrice() + " kr");

        // Discount tree
        Sellable thirdDiscount = new DiscountDecorator(secondDiscount, 3);
        System.out.println("Efter ytterligare 3% rabatt: " + thirdDiscount.getPrice() + " kr");

        System.out.println("\nTotalt sparar du: " + (phone.getPrice() - thirdDiscount.getPrice()) + " kr");
        System.out.println();
    }

    private static void demonstrateDifferentCustomers(ProductService productService) {
        System.out.println("3. OLIKA KUNDTYPER - OLIKA PRISER");
        System.out.println("==================================");

        Product book = productService.getProductById("BOOK001").orElseThrow();

        System.out.println("Produkt: " + book.getName());
        System.out.println("Grundpris: " + book.getPrice() + " kr\n");

        // Different customer types with different discounts
        Sellable regularCustomer = book;
        Sellable studentCustomer = new DiscountDecorator(book, 15);
        Sellable seniorCustomer = new DiscountDecorator(book, 20);
        Sellable vipCustomer = new DiscountDecorator(book, 30);

        System.out.println("Priser för olika kunder:");
        System.out.printf("- Ordinarie kund:        %.2f kr (ingen rabatt)%n", regularCustomer.getPrice());
        System.out.printf("- Student (-15%%):        %.2f kr%n", studentCustomer.getPrice());
        System.out.printf("- Senior (-20%%):         %.2f kr%n", seniorCustomer.getPrice());
        System.out.printf("- VIP medlem (-30%%):     %.2f kr%n", vipCustomer.getPrice());

        System.out.println("\nVIP-medlemmen sparar: " +
                (book.getPrice() - vipCustomer.getPrice()) + " kr!");
    }
}