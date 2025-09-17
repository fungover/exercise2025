package org.example.patterns.decorator;

import org.example.entities.Sellable;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class DiscountDecorator extends ProductDecorator {
    private final BigDecimal discountPercent;

    public DiscountDecorator(Sellable decoratedProduct, double discountPercentage) {
        super(decoratedProduct);
        if (discountPercentage < 0 || discountPercentage > 100) {
            throw new IllegalArgumentException("Discount percentage must be between 0 and 100: " +
                    discountPercentage);
        }

        this.discountPercent = BigDecimal.valueOf(discountPercentage);
    }

    @Override
    public BigDecimal getPrice() {
        BigDecimal discountFactor = BigDecimal.ONE
                .subtract(discountPercent.divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP));
        return decoratedProduct.getPrice()
                .multiply(discountFactor)
                .setScale(2, RoundingMode.HALF_UP);
    }
}

