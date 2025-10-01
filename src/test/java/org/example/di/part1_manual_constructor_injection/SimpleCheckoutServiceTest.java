package org.example.di.part1_manual_constructor_injection;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// These are unit tests:
// They verify the behavior of SimpleCheckoutService in isolation,
// using different PaymentMethod implementations.
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

    @Test
    void shouldPayWithCreditCard() {
        // Create a payment method and inject into the service
        PaymentMethod creditCard = new CreditCardPayment();
        CheckoutService checkout = new SimpleCheckoutService(creditCard);

        // Try to pay with 200 SEK
        String result = checkout.checkout(350);

        // Assert that the payment was successful
        assertEquals("Paid 350 SEK with Credit Card", result);
    }

    @Test
    void shouldPayWithTrustly() {
        // Create a payment method and inject into the service
        PaymentMethod payPal = new TrustlyPayment();
        CheckoutService checkout = new SimpleCheckoutService(payPal);

        // Try to pay with 200 SEK
        String result = checkout.checkout(500);

        // Assert that the payment was successful
        assertEquals("Paid 500 SEK with Trustly", result);
    }
}
