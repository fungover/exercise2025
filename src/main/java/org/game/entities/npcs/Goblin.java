package org.game.entities.npcs;

import org.game.entities.Enemy;
import org.game.entities.Item;

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
        addToLootTable(new Item("Coin","Currency",1,5));
        addToLootTable(new Item("Bones","Misc",0,1));
        addToLootTable(new Item("Bread","Consumable",1,1));
        addToLootTable(new Item("Bronze Axe","Weapon",5,1));
        addToLootTable(new Item("Bronze Sword","Weapon",5,1));
        addToLootTable(new Item("Bronze Shield","Off-Hand",5,1));
        addToLootTable(new Item("Bronze Helmet","Armour",5,1));
        addToLootTable(new Item("Bronze Platechest","Armour",15,1));
        addToLootTable(new Item("Bronze Platelegs","Armour",10,1));
        addToLootTable(new Item("Bronze Boots","Armour",5,1));
        addToLootTable(new Item("Bronze Gauntlets","Armour",5,1));

    }
}
