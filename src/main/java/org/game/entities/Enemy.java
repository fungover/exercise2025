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

        public List<Item> dropLoot() {
            List<Item> drops = new ArrayList<>();

            for (Item base : lootTable) {
                // Roll chance to drop
                if (!RandomGenerator.chance(50)) continue;

                Item drop;

                // Equipable items
                if (base.isEquippable()) {
                    drop = new Item(
                            base.getName(),
                            base.getType(),
                            base.getPrice(),
                            base.getQuantity(),
                            base.getSlots(),
                            base.getStrengthBonus(),
                            base.getDefenseBonus(),
                            base.getHealthBonus()
                    );
                }
                // Consumable items
                else if (base.isConsumable()) {
                    drop = new Item(
                            base.getName(),
                            base.getType(),
                            base.getPrice(),
                            base.getQuantity(),
                            true,
                            base.getHealAmount()
                    );
                }
                // Regular non-equipable, non-consumable items
                else {
                    drop = new Item(
                            base.getName(),
                            base.getType(),
                            base.getPrice(),
                            base.getQuantity()
                    );
                }

                drops.add(drop);
            }

            return drops;
        }
}
