package org.example.entities;

public class GiantHeadlessChicken extends Enemy {

    public GiantHeadlessChicken(int x, int y) {
        super("HeadlessChicken", 5, 20, x, y);
    }

    @Override
    public void attack(Player player) {
        player.takeDamage(damage);
        System.out.println("In a whirlwind of feathers and confusion, the " + name + " stomps you for " + damage + " damage!");
    }
}
