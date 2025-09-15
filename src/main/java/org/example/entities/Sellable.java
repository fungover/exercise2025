package org.example.entities;

// Something that can be sold at the warehouse

public interface Sellable {
    String getId();
    String getName();
    double getPrice();
}
