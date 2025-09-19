package org.example.decorators;

import org.example.entities.Sellable;

public class DiscountDecorator extends ProductDecorator {
    private final double discountPercentage;

    // --- Constructor ---
    public DiscountDecorator(Sellable sellable, double discountPercentage) {
        super(sellable); // Send to ProductDecorator constructor
        if (discountPercentage < 0 || discountPercentage > 100) {
            throw new IllegalArgumentException("Discount percentage must be between 0 and 100");
        }
        this.discountPercentage = discountPercentage; // Initiate fields
    }
@Override
    public double getPrice() {
        double originalPrice = sellable.getPrice(); // get original price
        return originalPrice * (1 - discountPercentage / 100); // calculate discounted price
    }
}
