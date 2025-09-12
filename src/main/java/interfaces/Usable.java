package interfaces;

import entities.Player;

/**
 * Interface för föremål som kan användas
 */
public interface Usable {
    String use(Player player);
    boolean isConsumable();
}
