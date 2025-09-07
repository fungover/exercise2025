package org.game.entities.npcs;

import org.game.entities.Enemy;
import org.game.entities.EquipmentSlot;
import org.game.entities.Item;

import java.util.List;

public class Goblin extends Enemy {
    public Goblin(int x, int y) {
       super("Goblin",
               10,
               3,
                5,
               0,
               0,
               0,
               0,
               0,
               0
       );

        // Todo drop table here
        // Item Name | Type | Price
        addToLootTable(new Item("coin","currency",1,5));
        addToLootTable(new Item("bones","misc",0,1));
        addToLootTable(new Item("bread","consumable",1,1));
        addToLootTable(new Item("bronze axe","weapon",5,1,List.of(EquipmentSlot.WEAPON), 1,0,0));
        addToLootTable(new Item("bronze sword","weapon",5,1,List.of(EquipmentSlot.WEAPON),1,0,0));
        addToLootTable(new Item("bronze shield","offhand",5,1,List.of(EquipmentSlot.OFFHAND),0,1,2));
        addToLootTable(new Item("bronze helmet","head",5,1,List.of(EquipmentSlot.HEAD),0,1,2));
        addToLootTable(new Item("bronze platechest","chest",15,1,List.of(EquipmentSlot.CHEST),0,1,2));
        addToLootTable(new Item("bronze platelegs","legs",10,1,List.of(EquipmentSlot.LEGS),0,1,2));
        addToLootTable(new Item("bronze boots","boot",5,1,List.of(EquipmentSlot.BOOTS),0,1,2));
        addToLootTable(new Item("bronze gauntlets","gloves",5,1,List.of(EquipmentSlot.GLOVES),0,1,2));

    }
}
