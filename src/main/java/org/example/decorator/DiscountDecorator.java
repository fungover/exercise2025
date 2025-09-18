package org.example.decorator;

public class DiscountDecorator extends ProductDecorator {

    /**
     * DiscoundDecorator - Adds discount functionality to any Sellable.
     * <p>
     * HOW IT WORKS:
     * - Takes original Sellable + discount percentage.
     * - Keeps ID and name unchanged (inherits from parent).
     * - Overrides getPrice() to apply discount.
     */

    private double discountPercentage;

    public DiscountDecorator(Sellable decoratedProduct, double discountPercentage) {
        super(decoratedProduct);
        this.discountPercentage = discountPercentage;
    }

    @Override
    public double getPrice() {
        double originalPrice = decoratedProduct.getPrice();
        double discountAmount = originalPrice * (discountPercentage / 100.0);
        return originalPrice - discountAmount;
    }
}
