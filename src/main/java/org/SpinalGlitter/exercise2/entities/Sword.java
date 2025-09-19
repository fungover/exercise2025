package org.SpinalGlitter.exercise2.entities;

public class Sword implements ItemObject{
    int damage = 10;

    public Sword(Position position) {
        String name = "Sword";
    }

    @Override
    public String printItem() {
        return "Sword (+" + damage + " DMG)";
    }

    @Override
    public String toString() {
        return "Sword";
    }

}


