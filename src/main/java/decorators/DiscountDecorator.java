package decorators;

import entities.Sellable;

// DiscountDecorator - Adds discount functionality to a product
// This is a concrete decorator that changes the price based on the discount percentage.

public class DiscountDecorator extends ProductDecorator {

    private final double discountPercentage;

    // Creating a discount decorator
    public DiscountDecorator(Sellable decoratedProduct, double discountPercentage) {
        super(decoratedProduct);

        if (discountPercentage < 0 || discountPercentage > 100) {
            throw new IllegalArgumentException("Discount percentage must be between 0 and 100, was: " + discountPercentage);
        }

        this.discountPercentage = discountPercentage;
    }

     //Override getPrice() to return discounted price
     // This is the essence of the Decorator Pattern - we change the behavior!
    @Override
    public double getPrice() {
        double originalPrice = decoratedProduct.getPrice();
        double discountAmount = originalPrice * (discountPercentage / 100.0);
        return originalPrice - discountAmount;
    }

    // Override getDescription() to include discount information
    @Override
    public String getDescription() {
        return decoratedProduct.getDescription() +
                String.format(" [%s rabatt - Nytt pris: %.2f kr]",
                        formatDiscount(), getPrice());
    }

    // Help method to get the discount percentage
    public double getDiscountPercentage() {
        return discountPercentage;
    }

    // Helper method for formatting discount text
    private String formatDiscount() {
        if (discountPercentage == (int) discountPercentage) {
            return (int) discountPercentage + "%";
        }
        return String.format("%.1f%%", discountPercentage);
    }

    // Help method to get the original price (before discount)
    public double getOriginalPrice() {
        return decoratedProduct.getPrice();
    }

    // Helpful method for calculating how much is saved
    public double getSavings() {
        return getOriginalPrice() - getPrice();
    }
}