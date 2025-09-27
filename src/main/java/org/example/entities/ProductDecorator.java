package org.example.entities;

import java.math.BigDecimal;

/**
 * Abstract decorator class that implements Sellable.
 * This class holds a reference to a Sellable object and delegates
 * all calls to it by default. Concrete decorators extend this class
 * and override specific methods to add new functionality.
 */
public abstract class ProductDecorator implements Sellable {
    
    protected final Sellable decoratedProduct;
    
    /**
     * Constructor that accepts a Sellable object to decorate.
     * 
     * @param decoratedProduct the product to be decorated
     */
    public ProductDecorator(Sellable decoratedProduct) {
        this.decoratedProduct = decoratedProduct;
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
    public BigDecimal getPrice() {
        return decoratedProduct.getPrice();
    }
}
