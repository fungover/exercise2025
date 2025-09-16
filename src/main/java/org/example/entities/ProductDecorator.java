package org.example.entities;

public abstract class ProductDecorator implements Sellable {
    protected Sellable decoratedProduct;

    public ProductDecorator(Sellable decoratedProduct) {
        this.decoratedProduct = decoratedProduct;
    }

    @Override
    public String getName() {
        return this.decoratedProduct.getName();
    }

    @Override
    public String getId() {
        return this.decoratedProduct.getId();
    }

    @Override
    public double getPrice() {
        return this.decoratedProduct.getPrice();
    }

}
