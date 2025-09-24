package org.example.entities;

import java.math.BigDecimal;

public abstract class ProductDecorator implements Sellable {
    protected Sellable decoratedProduct;

    public ProductDecorator(Sellable decoratedProduct) {
        this.decoratedProduct = decoratedProduct;
    }

    @Override
    public String name() {
        return decoratedProduct.name();
    }

    @Override
    public BigDecimal price() {
        return decoratedProduct.price();
    }

    @Override
    public String id() {
        return decoratedProduct.id();
    }
}
