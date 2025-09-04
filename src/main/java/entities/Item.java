package entities;

import entities.Player;

/**
 * Item är en abstraktion för alla föremål i spelet (t.ex. Potion, Weapon).
 */
public interface Item {
    String getName();
    void use(Player player);
}
