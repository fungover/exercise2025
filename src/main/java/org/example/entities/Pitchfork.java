package org.example.entities;

public class Pitchfork extends Item {
    private final int damage;

    public Pitchfork(int x, int y) {
        super("Rusty Pitchfork", x, y);
        this.damage = 10;
    }

    @Override
    public void use(Player player) {
        System.out.println("You jab with your " + name + " with farming fury! It deals " + damage + " damage.");
            }

    public int getDamage() {
        return damage;
    }
}

