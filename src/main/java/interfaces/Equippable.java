package interfaces;

import entities.Player;

/**
 * Interface for equippable items
 */
public interface Equippable {
    void equip(Player player);
    void unequip(Player player);
    boolean isEquipped();
    int getBonusValue();
}
