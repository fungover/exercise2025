package org.example.entities.equipment;

import org.example.entities.Player;

public interface Equippable {
    void equip(Player player);

    void unequip(Player player);

    EquipmentSlot getSlot();

    int getStatBonus();
}
