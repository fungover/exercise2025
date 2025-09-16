package org.example.entities;

public class DiscountDecorator extends ProductDecorator {
    private final double discountPercentage;

    public DiscountDecorator(Sellable decoratedProduct, double discountPercentage) {
        super(decoratedProduct);
        this.discountPercentage = discountPercentage;
    }
    
    @Override
    public double getPrice() {
        return decoratedProduct.getPrice() * (1 - discountPercentage / 100);
    }
}
