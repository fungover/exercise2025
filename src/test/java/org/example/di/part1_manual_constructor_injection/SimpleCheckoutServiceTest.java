package org.example.di.part1_manual_constructor_injection;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SimpleCheckoutServiceTest {

    @Test
    void shouldPayWithSwish() {
        // Create a payment method and inject into the service
        PaymentMethod swish = new SwishPayment();
        CheckoutService checkout = new SimpleCheckoutService(swish);

        // Try to pay with 200 SEK
        String result = checkout.checkout(200);

        // Assert that the payment was successful
        assertEquals("Paid 200 SEK with Swish", result);
    }
}
