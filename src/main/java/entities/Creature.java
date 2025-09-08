package entities;

/**
 * Creature är en grundtyp för alla levande varelser i spelet (t.ex. Player, Enemy).
 */
public interface Creature {
    int getHealth();
    void takeDamage(int amount);
    boolean isAlive();
}
