package org.example;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
@ApplicationScoped
class Engine {
    @Inject
    public Engine() {
        System.out.println("Engine created");
    }
}

@ApplicationScoped
class Transmission {
    @Inject
    public Transmission() {
        System.out.println("Transmission created");
    }
}

@ApplicationScoped
class Car {
    private final Engine engine;
    private final Transmission transmission;
    @Inject
    public Car(Engine engine, Transmission transmission) {
        this.engine = engine;
        this.transmission = transmission;
        System.out.println("Car created");
    }

    @Override
    public String toString() {
        return "Car with " + engine + " and " + transmission;
    }
}
