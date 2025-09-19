package org.example.patterns.decorator;

import org.example.entities.Sellable;
/*
  Abstract base class for decorators that add functionality to Sellable products.
 Implements the Decorator Pattern by wrapping a Sellable object.
 */
public abstract class ProductDecorator implements Sellable {
    //Holds a reference to the Sellable object being decorated
    protected final Sellable decoratedProduct;

    //Constructor accepts a Sellable object to wrap.
    public ProductDecorator(Sellable decoratedProduct) {
        this.decoratedProduct = decoratedProduct;
    }
    //Delegates getId(), getName() and getPrice() to the wrapped product.
    @Override
    public String getId() {
        return decoratedProduct.getId();
    }

    @Override
    public String getName() {
        return decoratedProduct.getName();
    }

    @Override
    public java.math.BigDecimal getPrice() {
        return decoratedProduct.getPrice();
    }
}
