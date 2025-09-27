package org.example;

import org.example.entities.*;

import java.math.BigDecimal;

public class DecoratorPatternDemo {
    public static void main(String[] args) {
        System.out.println("🎨 Decorator Pattern Demo");
        System.out.println("=========================");

        //--Create a regular product--
        System.out.println("\n💻 Step 1: Create a base product");
        Product laptop = new Product.Builder()
            .id("LAPTOP001")
            .name("MacBook Pro")
            .category(Category.ELECTRONICS)
            .rating(10)
            .price(new BigDecimal("2500.00"))
            .build();

        System.out.println("Original Product:");
        System.out.println("Name: " + laptop.getName());
        System.out.println("ID: " + laptop.getId());
        System.out.println("Price: $" + laptop.getPrice());

        //-- Apply discount decorator --
        System.out.println("\n🏷️ Step 2: Apply 20% discount using Decorator");
        Sellable discountedLaptop = new DiscountDecorator(laptop, 20.0);

        System.out.println("Discounted Product:");
        System.out.println("Name: " + discountedLaptop.getName()); // Same name
        System.out.println("ID: " + discountedLaptop.getId());     // Same ID
        System.out.println("Original Price: $" + ((DiscountDecorator)discountedLaptop).getOriginalPrice());
        System.out.println("Discounted Price: $" + discountedLaptop.getPrice()); // Modified price!
        System.out.println("Discount: " + ((DiscountDecorator)discountedLaptop).getDiscountPercentage() + "%");

        //-- decorator pattern flexibility --
        System.out.println("\n🔄 Step 3: Decorator Pattern Benefits");
        System.out.println("• Original object unchanged: $" + laptop.getPrice());
        System.out.println("• Decorated object modified: $" + discountedLaptop.getPrice());
        System.out.println("• Can be applied to any Sellable object");
        System.out.println("• Behavior added dynamically at runtime");

        //-- Multiple discounts--
        System.out.println("\n🎯 Step 4: Chaining decorators (additional 10% discount)");
        Sellable doubleDiscounted = new DiscountDecorator(discountedLaptop, 10.0);

        System.out.println("Double Discounted Product:");
        System.out.println("After 20% + 10% discounts: $" + doubleDiscounted.getPrice());
        System.out.println("Total savings: $" + laptop.getPrice().subtract(doubleDiscounted.getPrice()));

        //-- Different products, same decorator --
        System.out.println("\n📱 Step 5: Same decorator, different products");
        Product phone = new Product.Builder()
            .id("PHONE001")
            .name("iPhone 15")
            .category(Category.ELECTRONICS)
            .rating(9)
            .price(new BigDecimal("1000.00"))
            .build();

        Sellable discountedPhone = new DiscountDecorator(phone, 15.0);

        System.out.println("iPhone with 15% discount:");
        System.out.println("Original: $" + phone.getPrice());
        System.out.println("Discounted: $" + discountedPhone.getPrice());

        // xx Validation --
        System.out.println("\n❌ Step 6: Discount validation");
        try {
            new DiscountDecorator(laptop, 150.0); // Invalid discount > 100%
        } catch (IllegalArgumentException e) {
            System.out.println("✅ Validation works: " + e.getMessage());
        }

        // -- polymorphism--
        System.out.println("\n🔗 Step 7: Polymorphism in action");
        Sellable[] products = {laptop, discountedLaptop, doubleDiscounted, discountedPhone};

        System.out.println("All products original and decorated:");
        for (Sellable product : products) {
            System.out.println("- " + product.getName() + ": $" + product.getPrice());
        }

        System.out.println("\n🎉 Decorator Pattern successfully implemented!");
        System.out.println("✨ Products can be dynamically enhanced without changing their code!");
    }
}
