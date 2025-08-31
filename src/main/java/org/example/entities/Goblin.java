package org.example.entities;

public class Goblin extends Enemy {
    private static boolean swordDropped = false;

    public Goblin(Position position) {
        super("Goblin", 25, 8, position);
    }

    @Override
    public void attack(Player player) {
        player.takeDamage(getDamage());
        System.out.println(getAttackMessage());
    }

    @Override
    public String getAttackMessage() {
        return ">The Goblin stabs you with its sword for " + getDamage() + " damage!";
    }

    @Override
    public void dropLoot(Player player) {
        if (!swordDropped) {
            swordDropped = true;
            IronSword droppedSword = new IronSword(player.getPosition());
            player.addToInventory(droppedSword);
            System.out.println(">The Goblin has fallen and dropped an Iron Sword! It has been added to your inventory.");
        } else {
            System.out.println(">No loot dropped.");
        }
    }
}
