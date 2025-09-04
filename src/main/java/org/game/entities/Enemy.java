package org.game.entities;

import org.game.utils.RandomGenerator;

import java.util.ArrayList;
import java.util.List;

public class Enemy extends Character {
    private List<Item> lootTable = new ArrayList<>();

    public Enemy(String name, int health, int strength, int dexterity, int intelligence, int wisdom, int charisma, int defense, int x,
            int y
    ) {
        super(name,
                health,
                strength,
                dexterity,
                intelligence,
                wisdom,
                charisma,
                defense,
                x,
                y);
    }

            // Todo adds item to loot table
            protected void addToLootTable(Item item) {
                lootTable.add(item);
            }

            // Todo rolls for loot when enemy dies
            public Item getLoot() {
                if (lootTable.isEmpty())return null;
                if (!RandomGenerator.chance(50)) {
                    return null;
                }
                return lootTable.get(RandomGenerator.randomInt(0, lootTable.size() -1));
            }
}
