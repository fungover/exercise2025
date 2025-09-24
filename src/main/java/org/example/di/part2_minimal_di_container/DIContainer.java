package org.example.di.part2_minimal_di_container;

import org.example.di.part1_manual_constructor_injection.*;

public class DIContainer {
    public <T> T getInstance(Class<T> clazz) {
        // Hardcode: always returns customer with swish payment
        if (clazz == Customer.class) {
            return (T) new Customer(new SimpleCheckoutService(new SwishPayment()));
        }
        throw new IllegalArgumentException("Unsupported class: " + clazz);
    }
}
