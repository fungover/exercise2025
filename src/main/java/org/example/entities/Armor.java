package org.example.entities;

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
        System.out.println("Equipped " + name + "! Defense increased by " + armorValue);
    }

    @Override
    public void unequip(Player player) {
        System.out.println("Unequipped " + name + "! Defense decreased by " + armorValue);
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
