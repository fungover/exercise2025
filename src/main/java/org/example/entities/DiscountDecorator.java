package org.example.entities;

public class DiscountDecorator extends ProductDecorator {
    private double discountPercentage;

    public DiscountDecorator(Sellable product, double discountPercentage) {
        super(product);
        this.discountPercentage = discountPercentage;
    }

    @Override public double getPrice() {
        double originalPrice = super.getPrice(); // get original price
        double discount = originalPrice * (discountPercentage / 100.0);
        return originalPrice - discount; //returns the discounted price
    }
}
