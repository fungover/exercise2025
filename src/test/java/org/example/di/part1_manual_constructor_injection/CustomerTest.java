package org.example.di.part1_manual_constructor_injection;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// These tests are closer to integration tests:
// They verify the interaction between Customer, CheckoutService, and PaymentMethod.
public class CustomerTest {

    @Test
    void customerShouldBeAbleToPayWithSwish() {

        // Create a swish payment method
        PaymentMethod swish = new SwishPayment();

        // Create a checkout service with the swish payment method
        CheckoutService checkout = new SimpleCheckoutService(swish);

        // Create a customer that uses the checkout service
        Customer customer = new Customer(checkout);

        // Customer makes a purchase
        String result = customer.makePurchase(200);

        // Assert that the payment was successful
        assertEquals("Customer completed purchase: Paid 200 SEK with Swish", result);
    }

    @Test
    void customerShouldBeAbleToPayWithCreditCard() {

        // Create a credit card payment method
        PaymentMethod creditCard = new CreditCardPayment();

        // Create a checkout service with the credit card payment method
        CheckoutService checkout = new SimpleCheckoutService(creditCard);

        // Create a customer that uses the checkout service
        Customer customer = new Customer(checkout);

        // Customer makes a purchase
        String result = customer.makePurchase(350);

        // Assert that the payment was successful
        assertEquals("Customer completed purchase: Paid 350 SEK with Credit Card", result);
    }

    @Test
    void customerShouldBeAbleToPayWithTrustly() {

        // Create a trustly payment method
        PaymentMethod trustly = new TrustlyPayment();

        // Create a checkout service with the trustly payment method
        CheckoutService checkout = new SimpleCheckoutService(trustly);

        // Create a customer that uses the checkout service
        Customer customer = new Customer(checkout);

        // Customer makes a purchase
        String result = customer.makePurchase(500);

        // Assert that the payment was successful
        assertEquals("Customer completed purchase: Paid 500 SEK with Trustly", result);
    }
}
