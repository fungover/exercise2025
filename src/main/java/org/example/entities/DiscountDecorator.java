package org.example.entities;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Concrete decorator that applies a discount to a sellable product.
 * This decorator modifies the price behavior by applying a percentage
 * discount while keeping all other properties unchanged.
 */
public class DiscountDecorator extends ProductDecorator {
    
    private final double discountPercentage;
    
    /**
     * Creates a discount decorator for the given product.
     * 
     * @param decoratedProduct the product to apply discount to
     * @param discountPercentage the discount percentage (e.g., 20 for 20% off)
     * @throws IllegalArgumentException if discount percentage is negative or > 100
     */
    public DiscountDecorator(Sellable decoratedProduct, double discountPercentage) {
        super(decoratedProduct);
        
        if (discountPercentage < 0 || discountPercentage > 100) {
            throw new IllegalArgumentException("Discount percentage must be between 0 and 100");
        }
        
        this.discountPercentage = discountPercentage;
    }
    
    /**
     * Returns the discounted price.
     * Calculates: originalPrice * (1 - discountPercentage/100)
     * 
     * @return the discounted price, rounded to 2 decimal places
     */
    @Override
    public BigDecimal getPrice() {
        BigDecimal originalPrice = decoratedProduct.getPrice();
        BigDecimal discountMultiplier = BigDecimal.valueOf(1 - (discountPercentage / 100));
        return originalPrice.multiply(discountMultiplier).setScale(2, RoundingMode.HALF_UP);
    }
    
    /**
     * Gets the discount percentage applied to this product.
     * 
     * @return the discount percentage
     */
    public double getDiscountPercentage() {
        return discountPercentage;
    }
    
    /**
     * Gets the original price before discount.
     * 
     * @return the original price
     */
    public BigDecimal getOriginalPrice() {
        return decoratedProduct.getPrice();
    }
}
