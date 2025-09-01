package org.example.entities.items.armor;

import org.example.entities.*;
import org.example.entities.equipment.EquipmentSlot;
import org.example.entities.equipment.Equippable;

public abstract class Armor extends Item implements Equippable {
    private final EquipmentSlot slot;
    private final int armorValue;

    public Armor(String name, String description, Position position, EquipmentSlot slot, int armorValue) {
        super(name, description, position);
        this.slot = slot;
        this.armorValue = armorValue;
    }

    @Override
    public void equip(Player player) {
        player.setDefense(player.getDefense() + getStatBonus());
        System.out.println("Equipped " + getName() + "! Defense increased by " + getStatBonus() + ".");
    }

    @Override
    public void unequip(Player player) {
        player.setDefense(player.getDefense() - getStatBonus());
        System.out.println("Unequipped " + getName() + "! Defense decreased by " + getStatBonus());
    }

    @Override
    public EquipmentSlot getSlot() {
        return slot;
    }

    @Override
    public int getStatBonus() {
        return armorValue;
    }

}
