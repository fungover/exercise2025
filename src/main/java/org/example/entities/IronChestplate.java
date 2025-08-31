package org.example.entities;

public class IronChestplate extends Armor {
    public IronChestplate(Position position) {
        super("Iron Chestplate", "A sturdy chest armor. +4 Defense", position, EquipmentSlot.CHESTPLATE, 4);
    }
}
