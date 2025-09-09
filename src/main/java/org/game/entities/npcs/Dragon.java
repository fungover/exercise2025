package org.game.entities.npcs;

import org.game.entities.Enemy;
import org.game.entities.EquipmentSlot;
import org.game.entities.Item;

import java.util.List;

public class Dragon extends Enemy {
    public Dragon(int x, int y) {
        super("Dragon",
                25,
                5,
                6,
                3,
                0,
                0,
                5,
                0,
                0
        );

        // Todo drop table here
        //   Item Name    |     Type   |      Price    |   Quantity   |      SlotType      |
        addToLootTable(new Item("coin","currency",1,5));
        addToLootTable(new Item("bread","consumable",1,1,true,5));
        addToLootTable(new Item("healing potion","consumable",1,1,true,10));
        addToLootTable(new Item("steel axe","weapon",5,1,List.of(EquipmentSlot.WEAPON),3,0,0));
        addToLootTable(new Item("steel sword","weapon",5,1,List.of(EquipmentSlot.WEAPON),3,0,0));
        addToLootTable(new Item("steel shield","offhand",5,1,List.of(EquipmentSlot.OFFHAND),0,3,4));
        addToLootTable(new Item("steel helmet","head",5,1,List.of(EquipmentSlot.HEAD),0,3,4));
        addToLootTable(new Item("steel platechest","chest",15,1,List.of(EquipmentSlot.CHEST),0,3,4));
        addToLootTable(new Item("steel platelegs","legs",10,1,List.of(EquipmentSlot.LEGS),0,3,4));
        addToLootTable(new Item("steel boots","boot",5,1,List.of(EquipmentSlot.BOOTS),0,3,4));
        addToLootTable(new Item("steel gauntlets","gloves",5,1,List.of(EquipmentSlot.GLOVES),0,3,4));

    }
}
