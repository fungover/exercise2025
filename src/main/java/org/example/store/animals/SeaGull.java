package org.example.store.animals;

public class SeaGull extends Animal  implements SeaCreature, Airborne{
    @Override
    public String fly() {
        return "Strongly up to 50 feet high";
    }

    @Override
    public String name() {
        return "Sea Gull";
    }

    @Override
    public String preferredFood() {
        return "Anything";
    }

    @Override
    public String swim() {
        return "as deep as 40 feet";
    }
}
