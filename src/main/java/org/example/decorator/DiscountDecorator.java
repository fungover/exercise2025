package org.example.decorator;

public class DiscountDecorator extends ProductDecorator {

    private double discountPercentage;

    public DiscountDecorator(Sellable decoratedProduct, double discountPercentage) {
        super(decoratedProduct);
        this.discountPercentage = discountPercentage;
    }

    @Override
    public double getPrice() {
        double originalPrice = decoratedProduct.getPrice();
        double discountAmount = originalPrice * (discountPercentage / 100.0);
        return originalPrice - discountAmount;
    }
}
