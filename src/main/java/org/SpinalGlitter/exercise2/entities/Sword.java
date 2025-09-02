package org.SpinalGlitter.exercise2.entities;

public class Sword implements ItemObject{

    private String name;
    int damage = 10;
    private final Position position;


    public Sword(Position position) {
        this.position = position;
        this.name = "Sword";
    }

    public Sword(String name, int damage, Position position) {
        this.name = name;
        this.damage = damage;
        this.position = position;
    }

    @Override
    public String printItem() {
        return "Sword (+" + damage + " DMG)";
    }
}
