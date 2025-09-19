package org.example.entities;

import java.util.Objects;

abstract class ProductDecorator implements Sellable {
    protected Sellable decoratedProduct;

    public ProductDecorator(Sellable decoratedProduct) {
        this.decoratedProduct = Objects.requireNonNull(decoratedProduct, "decoratedProduct");
    }

    @Override
    public String getId() {
        return decoratedProduct.getId();
    }
    @Override
    public String getName() {
        return decoratedProduct.getName();
    }

    @Override
    public double getPrice() {
        return decoratedProduct.getPrice();
    }
}
