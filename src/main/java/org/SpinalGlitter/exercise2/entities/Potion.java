package org.SpinalGlitter.exercise2.entities;

public class Potion {
    private final String name = "Potion";
    private final int heal = 20;
    private final Position position;

    public Potion(Position position) { this.position = position; }

    public String getName() { return name; }
    public int getHeal() { return heal; }
    public Position getPosition() { return position; }
}
