package org.example.decorator;

import org.example.entities.Sellable;

public final class DiscountDecorator extends ProductDecorator {
    private final double discountPercentage;

    public DiscountDecorator(Sellable product, double discountPercentage) {
        super(product);
        if (discountPercentage < 0 || discountPercentage > 100) {
            throw new IllegalArgumentException("discountPercentage must be 0..100");
        }
        this.discountPercentage = discountPercentage;
    }

    @Override
    public double getPrice() {
        double base = decoratedProduct.getPrice();
        return base * (1.0 - discountPercentage / 100.0);
    }
}
