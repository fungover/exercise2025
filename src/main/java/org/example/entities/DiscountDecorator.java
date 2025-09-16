package org.example.entities;

public class DiscountDecorator extends ProductDecorator {
    private final double discountPercentage;

    public DiscountDecorator(Sellable decoratedProduct, double discountPercentage) {
        super(decoratedProduct);

        if (discountPercentage < 0 || discountPercentage > 100) {
            throw new IllegalArgumentException("Discount percentage must be between 0 and 100");
        }
        if (decoratedProduct == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }

        this.discountPercentage = discountPercentage;
    }

    @Override
    public double getPrice() {
        return this.decoratedProduct.getPrice() * (1 - this.discountPercentage / 100.0);
    }

}
