package org.example.di.part3_weld_cdi;

import jakarta.enterprise.context.ApplicationScoped;

//@ApplicationScoped // Weld will manage this as a bean
public class TrustlyPayment implements PaymentMethod {
    @Override
    public String pay(int amount) {
        return "Paid " + amount + " SEK with Trustly";
    }
}
