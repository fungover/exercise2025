package org.example.part3;

import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;

public class Part3Main {
    public static void main(String[] args) {
        System.out.println("\nWeld CDI\n");

        Weld weld = new Weld();

        try (WeldContainer container = weld.initialize()) {

            ComputerBuilder builder = container.select(ComputerBuilder.class).get();

            String gamingPC = builder.buildComputer("Gaming");
            System.out.println(gamingPC);

            String officePC = builder.buildComputer("Office");
            System.out.println(officePC);

            System.out.println("Weld CDI completed successfully!");
        } // Container closed automatically here, even if an exception is thrown.
    }

}

