package org.example.entities.items.armor;

import org.example.entities.equipment.EquipmentSlot;
import org.example.entities.Position;

public class IronBoots extends Armor {
    public IronBoots(Position position) {
        super("Iron Boots", "Sturdy boots made of iron. +2 Defense.", position, EquipmentSlot.BOOTS, 2);
    }
}
