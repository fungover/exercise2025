package org.example.store.animals;

public class ButterFly extends Animal implements Airborne {

    @Override
    public String name() {
        return "Butterfly";
    }

    @Override
    public String preferredFood() {
        return "Nectar and Sugarwater";
    }

    @Override
    public String fly() {
        return "Not high but very random";
    }
}
