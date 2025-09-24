package org.example.entities;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class DiscountDecorator extends ProductDecorator {
    private double discountPercentage;

    public DiscountDecorator(Sellable product, double discountPercentage) {
        super(product);
        this.discountPercentage = discountPercentage;
    }

    @Override
    public BigDecimal price() {
        BigDecimal oldPrice = super.price();
        if (oldPrice != null) {
            BigDecimal discount = oldPrice.multiply(BigDecimal
                    .valueOf(discountPercentage / 100));
            return oldPrice.subtract(discount).setScale(2,
                    RoundingMode.HALF_UP);
        }
        return BigDecimal.ZERO;
    }
}
