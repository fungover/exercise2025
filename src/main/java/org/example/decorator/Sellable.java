package org.example.decorator;

/**
 * Sellable interface - Contract for objects that can be sold.
 * <p>
 * PURPOSE:
 * - Both Product and Decorators implement this interface.
 * - Makes decorators transparent - you can use decorated/undecorated objects interchangeably.
 */

public interface Sellable {

    String getId();

    String getName();

    double getPrice();
}
