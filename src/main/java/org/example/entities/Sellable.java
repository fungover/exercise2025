package org.example.entities;

import java.math.BigDecimal;

/**
 * Interface for sellable items that have basic product information
 * and pricing. This interface enables the Decorator Pattern by
 * providing a common contract for both concrete products and decorators.
 */
public interface Sellable {
    
    /**
     * Gets the unique identifier of the sellable item.
     * 
     * @return the ID of the item
     */
    String getId();
    
    /**
     * Gets the name of the sellable item.
     * 
     * @return the name of the item
     */
    String getName();
    
    /**
     * Gets the price of the sellable item.
     * This method can be overridden by decorators to modify pricing.
     * 
     * @return the price of the item
     */
    BigDecimal getPrice();
}
