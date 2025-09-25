package org.example.di.part3_weld_cdi;

import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class WeldIntegrationTest {

    @Test
    void shouldCreateCustomerWithDependenciesUsingWeld() {
        // Start Weld (the CDI container) for this test
        try (WeldContainer container = new Weld().initialize()) {

            // Ask Weld for a Customer instance
            // Weld should automatically create Customer,
            // and also inject CheckoutService + PaymentMethod
            Customer customer = container.select(Customer.class).get();

            // Use the customer to make a purchase
            String result = customer.makePurchase(200);

            // Check that the dependencies worked correctly
            assertEquals("Customer completed purchase: Paid 200 SEK with Swish", result);
        }
    }

    @Test
    void customerShouldBeAbleToChooseBetweenDifferentPaymentMethods() {
        try (WeldContainer container = new Weld().initialize()) {

            Customer customer = container.select(Customer.class).get();

            // Customer chooses Swish
            String swishResult = customer.makePurchase(200, "swish");
            assertEquals("Customer completed purchase: Paid 200 SEK with Swish", swishResult);

            // Customer chooses Credit Card
            String creditResult = customer.makePurchase(300, "creditCard");
            assertEquals("Customer completed purchase: Paid 300 SEK with Credit Card", creditResult);

            // Customer chooses Trustly
            String trustlyResult = customer.makePurchase(400, "trustly");
            assertEquals("Customer completed purchase: Paid 400 SEK with Trustly", trustlyResult);
        }
    }
}
