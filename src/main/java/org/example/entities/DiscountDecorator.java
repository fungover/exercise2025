package org.example.entities;

public class DiscountDecorator extends ProductDecorator {
    private final double discountPercentage;

    public DiscountDecorator(Sellable product, double discountPercentage) {
        super(product);
        if (Double.isNaN(discountPercentage) || discountPercentage < 0.0 ||
          discountPercentage > 100) {
            throw new IllegalArgumentException(
              "discountPercentage must be between in [0,100]");
        }
        this.discountPercentage = discountPercentage;
    }

    @Override public double getPrice() {
        double originalPrice = super.getPrice(); // get original price
        double discount = originalPrice * (discountPercentage / 100.0);
        double discounted = originalPrice - discount;
        //round to 2 decimals (currency like)
        return Math.round(discounted * 100.0) / 100.0d;
    }
}
