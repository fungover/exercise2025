package org.example.di.part1_manual_constructor_injection;

// Implementation of PaymentMethod using Credit Card
public class CreditCardPayment implements PaymentMethod {
    @Override
    public String pay(int amount) {
        return "Paid " + amount + " SEK with Credit Card";
    }
}
