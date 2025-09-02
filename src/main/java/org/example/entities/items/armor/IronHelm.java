package org.example.entities.items.armor;

import org.example.entities.equipment.EquipmentSlot;
import org.example.entities.Position;

public class IronHelm extends Armor {
    public IronHelm(Position position) {
        super("Iron Helm", "A sturdy iron helmet +2 Defense", position, EquipmentSlot.HELMET, 2);
    }
}
