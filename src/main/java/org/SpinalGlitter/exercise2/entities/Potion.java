package org.SpinalGlitter.exercise2.entities;

public class Potion implements ItemObject{
    private final String name = "Potion";
    private final Position position;

    public Potion(Position position) { this.position = position; }

    public String getName() { return name; }
    public Position getPosition() { return position; }

    @Override
    public String printItem() {
        int heal = 20;
        return name + " (+" + heal + " HP)";
    }

    @Override
    public String toString() {
        return printItem();
    }
}
