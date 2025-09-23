package org.example.entities.decorator;

// DiscountDecorator är en dekoratör som lägger till en ny funktion:
// den kan ändra priset genom att applicera en rabatt.
public class DiscountDecorator extends ProductDecorator {

    private final double discountPercentage;

    // Konstruktor: skickar in den produkt som ska wrappas
    // och en rabattprocent som ska appliceras
    public DiscountDecorator(Sellable decoratedProduct, double discountPercentage) {
        super(decoratedProduct);
        if (Double.isNaN(discountPercentage) || Double.isInfinite(discountPercentage)) {
            throw new IllegalArgumentException("discountPercentage must be a finite number");
            }
        if (discountPercentage < 0.0 || discountPercentage > 100.0) {
            throw new IllegalArgumentException("discountPercentage must be between 0 and 100");
            }
        this.discountPercentage = discountPercentage;
    }

    @Override
    public double getPrice() {
        // Hämtar orginalpriset från produkten
        double originalPrice = decoratedProduct.getPrice();

        // Räknar ut rabatten
        double discount  = originalPrice * (discountPercentage / 100.0);

        // Returnerar nya priset
        return originalPrice - discount ;
    }
}
