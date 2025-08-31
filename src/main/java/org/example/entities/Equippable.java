package org.example.entities;

public interface Equippable {
    void equip(Player player);

    void unequip(Player player);

    EquipmentSlot getSlot();

    int getStatBonus();
}
