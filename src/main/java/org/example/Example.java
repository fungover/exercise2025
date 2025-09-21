package org.example;

class Engine {
    public Engine() {
        System.out.println("Engine created");
    }
    public void start() {
        System.out.println("Engine started");
    }
}

class Transmission {
    public Transmission() {
        System.out.println("Transmission created");
    }
}

class Car {
    private final Engine engine;
    private final Transmission transmission;

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
