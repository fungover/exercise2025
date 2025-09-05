package org.example.entities;

public class Pitchfork extends Item implements Weapon {
    private final int damage = 15;

    public Pitchfork(int x, int y) {
        super("Rusty Pitchfork", x, y);
    }

    @Override
    public void use(Player player) {
        System.out.println("You jab with your " + getName() + " with farming fury! It deals " + damage + " damage.");
            }
    @Override
    public int getDamage() {
        return damage;
    }
}

