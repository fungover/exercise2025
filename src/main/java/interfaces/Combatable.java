package interfaces;

/**
 * Interface for entities that can fight
 */
public interface Combatable {
    int attack();
    void takeDamage(int damage);
    boolean isAlive();
    int getCurrentHealth();
    int getMaxHealth();
}