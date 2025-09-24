package org.example.di.part2_minimal_di_container;
import org.example.di.part1_manual_constructor_injection.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DIContainerTest {

    @Test
    void shouldCreateCustomerWithDependencies() {
        // Create a DI container
        DIContainer container = new DIContainer();

        // Ask for a Customer instance
        Customer customer = container.getInstance(Customer.class);

        // Assert that the whole dependency graph was created
        String result = customer.makePurchase(200);
        assertEquals("Customer completed purchase: Paid 200 SEK with Swish", result);
    }
}
