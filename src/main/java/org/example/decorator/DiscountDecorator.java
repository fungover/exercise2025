package org.example.decorator;

public class DiscountDecorator extends ProductDecorator {

    /**
     * DiscountDecorator - Adds discount functionality to any Sellable.
     * <p>
     * HOW IT WORKS:
     * - Takes original Sellable + discount percentage.
     * - Keeps ID and name unchanged (inherits from parent).
     * - Overrides getPrice() to apply discount.
     */

    private final double discountPercentage;

    public DiscountDecorator(Sellable decoratedProduct, double discountPercentage) {
        super(decoratedProduct);
        if (discountPercentage < 0 || discountPercentage > 100) {
            throw new IllegalArgumentException("Discount percentage must be between 0 and 100");
        }
        this.discountPercentage = discountPercentage;
    }

    @Override
    public double getPrice() {
        double originalPrice = decoratedProduct.getPrice();
        double discountAmount = originalPrice * (discountPercentage / 100.0);
        return originalPrice - discountAmount;
    }
}
