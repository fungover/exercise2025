package org.example.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerItemTest {

    @Test
    void potion_restores_hp() {
        Player player = new Player("Player");
        player.setHp(50);

        Item potion = new Item("Potion", Item.ItemType.CONSUMABLE, 50);
        player.addToInventory(potion);

        player.useItem(potion);

        assertEquals(100, player.getHp(), "Potion should restore 50 HP");
        assertTrue(player.getInventory().isEmpty(), "Inventory should be empty after using the potion");
    }

    @Test
    void weapon_increases_attack_damage() {
        Player player = new Player("Player");
        int baseAttack = player.getAttackDamage();

        Item sword = new Item("Iron Sword", Item.ItemType.WEAPON, 10);
        player.addToInventory(sword);

        player.useItem(sword);

        assertEquals(baseAttack + 10, player.getAttackDamage(),
                "Attack damage should increase by weapon effect value");
        assertTrue(player.getInventory().isEmpty(),
                "Inventory should be empty after equipping the weapon");
    }

    @Test
    void armor_increases_defense() {
        Player player = new Player("Player");
        int baseDefense = player.getDefense();

        Item armor = new Item("Leather Armor", Item.ItemType.ARMOR, 5);
        player.addToInventory(armor);

        player.useItem(armor);

        assertEquals(baseDefense + 5, player.getDefense(),
                "Defense should increase by armor effect value");
        assertTrue(player.getInventory().isEmpty(),
                "Inventory should be empty after equipping the armor");
    }

    @Test
    void equipping_new_weapon_returns_old_to_inventory() {
        Player player = new Player("Player");

        Item sword = new Item("Iron Sword", Item.ItemType.WEAPON, 10);
        Item axe = new Item("Battle Axe", Item.ItemType.WEAPON, 15);

        player.addToInventory(sword);
        player.useItem(sword);

        player.addToInventory(axe);
        player.useItem(axe);

        // Kolla att Iron Sword ligger i inventory
        assertTrue(player.getInventory().stream()
                        .anyMatch(i -> i.getName().equals("Iron Sword")),
                "Old weapon (Iron Sword) should be returned to inventory when equipping new weapon");

        // Kolla att attackDamage nu baseras på Battle Axe
        assertEquals(20 + 15, player.getAttackDamage(),
                "Attack damage should now be based on Battle Axe");
    }


    @Test
    void equipping_new_armor_returns_old_to_inventory() {
        Player player = new Player("Player");

        Item leather = new Item("Leather Armor", Item.ItemType.ARMOR, 5);
        Item chainmail = new Item("Chainmail", Item.ItemType.ARMOR, 10);

        player.addToInventory(leather);
        player.useItem(leather);

        player.addToInventory(chainmail);
        player.useItem(chainmail);

        assertEquals("Leather Armor", player.getInventory().isEmpty() ? null : player.getInventory().get(0).getName(),
                "Old armor should be returned to inventory when equipping new armor");
        assertEquals(10, player.getDefense(),
                "Defense should now be based on Chainmail");
    }
}
