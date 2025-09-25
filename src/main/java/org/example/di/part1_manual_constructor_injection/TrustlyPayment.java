package org.example.di.part1_manual_constructor_injection;

// Implementation of PaymentMethod using Trustly
public class TrustlyPayment implements PaymentMethod {
    @Override
    public String pay(int amount) {
        return "Paid " + amount + " SEK with Trustly";
    }
}
