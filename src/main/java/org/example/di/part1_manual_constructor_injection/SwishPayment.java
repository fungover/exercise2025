package org.example.di.part1_manual_constructor_injection;

// Implementation of PaymentMethod using Swish
public class SwishPayment implements PaymentMethod {
    @Override
    public String pay(int amount) {
        return "Paid " + amount + " SEK with Swish";
    }
}
