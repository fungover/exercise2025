package org.example.store.animals;

public abstract class Animal {
    public abstract String name();
    public abstract String preferredFood();

    public String eat() {
        return "Eating " + preferredFood();
    }
}
