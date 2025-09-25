package org.example.part2;

import org.example.part1.ComputerBuilder;

public class Part2Main {
    public static void main(String[] args) {
        System.out.println("\nCustom DI Container\n");

        // Create container
        SimpleContainer container = new SimpleContainer();

        // Just ask for what you want, container handles the rest
        System.out.println("Creating ComputerBuilder with all dependencies..\n");
        ComputerBuilder builder = container.getInstance(ComputerBuilder.class);

        if (builder != null) {
            System.out.println("Container successfully created ComputerBuilder!\n");

            String gamingPC = builder.buildComputer("Gaming");
            String officePC = builder.buildComputer("Office");

            System.out.println("Gaming PC:");
            System.out.println(gamingPC);
            System.out.println("\nOffice PC:");
            System.out.println(officePC);
        } else {
            System.out.println("Failed to create ComputerBuilder!");
        }
    }
}
