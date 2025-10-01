package org.example.di.part2_minimal_di_container;
import org.example.di.part1_manual_constructor_injection.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DIContainerTest {

    @Test
    void shouldCreateCustomerWithDependencies() {
        // We ask only for Customer, and the container resolves the full chain
        // (Customer -> CheckoutService -> PaymentMethod) automatically.
        Customer customer = new DIContainer()
                .getInstance(Customer.class);

        // NOTE: The container is currently hardcoded to use SwishPayment
        // for the PaymentMethod interface. So this test will always expect Swish.
        String result = customer.makePurchase(200);
        assertEquals("Customer completed purchase: Paid 200 SEK with Swish", result);
    }
}
