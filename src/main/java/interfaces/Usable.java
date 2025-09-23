package interfaces;

import entities.Player;

/**
 * Interface for usable objects
 */
public interface Usable {
    String use(Player player);
    boolean isConsumable();
}
