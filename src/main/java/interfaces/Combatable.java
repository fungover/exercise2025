package interfaces;

/**
 * Interface för entiteter som kan slåss
 */
public interface Combatable {
    int attack();
    void takeDamage(int damage);
    boolean isAlive();
    int getCurrentHealth();
    int getMaxHealth();
}