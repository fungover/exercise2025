package org.example.di.part3_weld_cdi;

import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;

public class MainPart3 {
    public static void main(String[] args) {
        try (WeldContainer container = new Weld().initialize()) {
            Customer customer = container.select(Customer.class).get();
            System.out.println(customer.makePurchase(100)); // default Swish
            System.out.println(customer.makePurchase(200, "creditcard"));
            System.out.println(customer.makePurchase(300, "trustly"));
            System.out.println(customer.makePurchase(400, "swish"));
        }
    }
}