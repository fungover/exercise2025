package org.example.di.part3_weld_cdi;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
@Trustly
public class TrustlyPayment implements PaymentMethod {
    @Override
    public String pay(int amount) {
        return "Paid " + amount + " SEK with Trustly";
    }
}
