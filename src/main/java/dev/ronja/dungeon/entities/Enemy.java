package dev.ronja.dungeon.entities;

/**
 * NO longer a base class. This is a contract with method signatures!
 * This interface needs to be implemented by all enemies
 **/
public interface Enemy {
    String getName();

    int getHp();

    int getDamage();

    boolean isAlive();

    void takeDamage(int amount);

    void attack(Player player);
}
