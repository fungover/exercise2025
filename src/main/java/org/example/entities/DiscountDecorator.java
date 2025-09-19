package org.example.entities;

import java.util.Objects;

public class DiscountDecorator extends ProductDecorator {
    private final double discountPercentage;

    public DiscountDecorator(Sellable decoratedProduct, double discountPercentage) {
        super(Objects.requireNonNull(decoratedProduct));
        if (discountPercentage < 0 || discountPercentage > 100) {
            throw new IllegalArgumentException("Discount percentage must be between 0 and 100");
        }

        this.discountPercentage = discountPercentage;
    }

    @Override
    public double getPrice() {
        return this.decoratedProduct.getPrice() * (1.0 - this.discountPercentage / 100.0);
    }

    public double getDiscountPercentage() {
        return this.discountPercentage;
    }

}
