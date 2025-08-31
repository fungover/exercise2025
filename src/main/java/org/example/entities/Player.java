package org.example.entities;

public class Player extends Character {
    public Player(String name, int maxHP, int baseDamage, Position position) {
        super(name, maxHP, baseDamage, position);
    }

    public void attack(Enemy enemy) {
        System.out.println(name + " attacks " + enemy.getName() + " for " + baseDamage + " damage!");
        enemy.takeDamage(baseDamage);
        System.out.println(enemy.getName() + " now has " + enemy.getHp() + " HP left.");
    }
}
