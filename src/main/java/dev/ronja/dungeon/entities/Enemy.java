package dev.ronja.dungeon.entities;

/**
 * NO longer a base class. This is a contract with method signatures!
 * This interface needs to be implemented by all enemies
 **/
public interface Enemy {
    String getName();

    int getHp();

    int getMaxHp();

    int getDamage();

    ///  Default boolean for all enemies
    default boolean isAlive() {
        return getHp() > 0;
    }

    ///  Default takeDamage()
    void setHp(int hp);

    default void takeDamage(int amount) {
        int dmg = Math.max(0, amount);
        int newHp = Math.max(0, getHp() - dmg);
        setHp(newHp);
    }

    void attack(Player player);

}
