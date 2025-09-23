package org.example;

import org.example.container.Container;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;

public class App {
    public static void main(String[] args) {
        System.out.println("Part 1: Manual Dependency Injection");
        manualDependencyInjection();

        System.out.println("\nPart 2: Container DI");
        containerDI();

        System.out.println("\nPart 3: Weld CDI");
        weldDI();
    }
    private static void manualDependencyInjection() {
        //Part 1 implementation with manual dependency injection
        var crimeRepo = new org.example.repository.CrimeArchive();
        var detective = new org.example.detective.SherlockHolmes(crimeRepo);
        var engine = new Investigation(detective);

        engine.startInvestigation("CASE-MANUAL");
    }

    private static void containerDI() {
        // Part 2 implementation: Use container to resolve the entire dependency graph
        Container container = new Container();

        // Ask container only for top-level class
        // Container will automatically resolve: Investigation -> Detective -> Crime
        Investigation investigation = container.getInstance(Investigation.class);

        investigation.startInvestigation("CASE-CONTAINER");

        System.out.println("Container successfully resolved entire dependency graph!");
    }

    //Part 3 implementation using Weld CDI
    private static void weldDI() {
        Weld weld = new Weld();
        try (WeldContainer container = weld.initialize()) {
            Investigation investigation = container.select(Investigation.class).get();
            investigation.startInvestigation("CASE-WELD");
        }
    }
}