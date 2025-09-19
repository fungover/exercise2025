package org.example.entities;

public abstract class ProductDecorator implements Sellable {
    protected Sellable decoratedProduct;

    public ProductDecorator(Sellable product) {
        this.decoratedProduct = product;
    }

    @Override public String getName() {
        return decoratedProduct.getName();
    }

    @Override public double getPrice() {
        return decoratedProduct.getPrice();
    }

    @Override public String getId() {
        return decoratedProduct.getId();
    }


}
