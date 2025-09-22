package org.example.decorator;

import org.example.entities.Sellable;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class DiscountDecorator extends ProductDecorator {
    private final double discountPercentage;

    public DiscountDecorator(Sellable decoratedProduct, double discountPercentage) {
        super(decoratedProduct);
        if (discountPercentage < 0 || discountPercentage > 100) {
            throw new IllegalArgumentException("Discount percentage must be between 0 and 100, it was: " + discountPercentage);
        }
        this.discountPercentage = discountPercentage;
    }

    @Override
    public BigDecimal getPrice() {
        BigDecimal originalPrice = decoratedProduct.getPrice();
        BigDecimal discountAmount = originalPrice.multiply(BigDecimal.valueOf(discountPercentage / 100));
        return originalPrice.subtract(discountAmount).setScale(2, RoundingMode.HALF_UP);
    }

}
