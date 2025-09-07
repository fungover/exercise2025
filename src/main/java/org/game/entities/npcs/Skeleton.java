package org.game.entities.npcs;

import org.game.entities.Enemy;
import org.game.entities.EquipmentSlot;
import org.game.entities.Item;

import java.util.List;

public class Skeleton extends Enemy {
    public Skeleton(int x, int y) {
        super("Skeleton",
                15,
                5,
                5,
                0,
                0,
                0,
                3,
                0,
                0
        );

        // Todo drop table here
        // Item Name | Type | Price
        addToLootTable(new Item("coin","currency",1,5));
        addToLootTable(new Item("bones","misc",0,1));
        addToLootTable(new Item("bread","consumable",1,1));
        addToLootTable(new Item("iron axe","weapon",5,1,List.of(EquipmentSlot.WEAPON),2,0,0));
        addToLootTable(new Item("iron sword","weapon",5,1,List.of(EquipmentSlot.WEAPON),2,0,0));
        addToLootTable(new Item("iron shield","offhand",5,1,List.of(EquipmentSlot.OFFHAND),0,2,3));
        addToLootTable(new Item("iron helmet","head",5,1,List.of(EquipmentSlot.HEAD),0,2,3));
        addToLootTable(new Item("iron platechest","chest",15,1,List.of(EquipmentSlot.CHEST),0,2,3));
        addToLootTable(new Item("iron platelegs","legs",10,1,List.of(EquipmentSlot.LEGS),0,2,3));
        addToLootTable(new Item("iron boots","boot",5,1,List.of(EquipmentSlot.BOOTS),0,2,3));
        addToLootTable(new Item("iron gauntlets","gloves",5,1,List.of(EquipmentSlot.GLOVES),0,2,3));

    }
}
