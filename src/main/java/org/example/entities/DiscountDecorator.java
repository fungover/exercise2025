package org.example.entities;

public class DiscountDecorator extends ProductDecorator {

    private final double discountPercentage;

    public DiscountDecorator(Product product, double discountPercentage) {
        super(product);

        if(discountPercentage < 0 || discountPercentage > 100) {
            throw new IllegalArgumentException("Invalid discount percentage");
        }
        this.discountPercentage = discountPercentage;
    }

    @Override
    public double getPrice() {
        double originalPrice = decoratedProduct.getPrice();
        return originalPrice - (originalPrice * (discountPercentage/100.0));
    }
}
