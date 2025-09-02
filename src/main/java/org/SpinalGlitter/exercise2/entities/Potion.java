package org.SpinalGlitter.exercise2.entities;

import org.SpinalGlitter.exercise2.utils.WorldObject;

public class Potion implements WorldObject {
    private final String name = "Potion";
    private final int heal = 20;
    private final Position position;

    public Potion(Position position) { this.position = position; }

    public String getName() { return name; }
    public int getHeal() { return heal; }
    public Position getPosition() { return position; }

    public String getSymbol() {return "💗";}
}
