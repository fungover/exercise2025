package org.example.di.part2_minimal_di_container;

import org.example.di.part1_manual_constructor_injection.Customer;

public class MainPart2 {
    public static void main(String[] args) {
        DIContainer container = new DIContainer();

        // Vi ber bara om Customer, containern bygger resten automatiskt
        Customer customer = container.getInstance(Customer.class);

        String result = customer.makePurchase(300);
        System.out.println(result);
    }
}