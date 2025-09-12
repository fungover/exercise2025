package interfaces;

import entities.Player;

/**
 * Interface för föremål som kan utrustas
 */
public interface Equippable {
    void equip(Player player);
    void unequip(Player player);
    boolean isEquipped();
    int getBonusValue();
}
