package org.example.entities;

public class Goblin extends Enemy {

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
        return "The Goblin stabs you with its dagger for " + getDamage() + " damage!";
    }

    public void berserkerMode() {
        modifyDamage(getDamage() + 5);
        System.out.println(getName() + " drinks something suspicious and looks angrier! Its damage increases to " + getDamage() + "!");
    }
}
