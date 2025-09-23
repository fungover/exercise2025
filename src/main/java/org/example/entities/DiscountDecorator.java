package org.example.entities;

import java.math.BigDecimal;

public class DiscountDecorator extends ProductDecorator {
    private double discountPercentage;

    public DiscountDecorator(Sellable product, double discountPercentage) {
        super(product);
        this.discountPercentage = discountPercentage;
    }

    @Override
    public BigDecimal price() {
        BigDecimal oldPrice = super.price();
        BigDecimal discount = oldPrice.multiply(BigDecimal.valueOf(discountPercentage));
        return oldPrice.subtract(discount);
    }
}
