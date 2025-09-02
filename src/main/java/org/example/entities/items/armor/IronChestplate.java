package org.example.entities.items.armor;

import org.example.entities.equipment.EquipmentSlot;
import org.example.entities.Position;

public class IronChestplate extends Armor {
    public IronChestplate(Position position) {
        super("Iron Chestplate", "A sturdy chest armor. +4 Defense", position, EquipmentSlot.CHESTPLATE, 4);
    }
}
