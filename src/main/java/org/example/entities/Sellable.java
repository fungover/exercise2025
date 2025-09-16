package org.example.entities;

import java.math.BigDecimal;

public interface Sellable {
    String getId();
    String getName();
    BigDecimal getPrice();
}
