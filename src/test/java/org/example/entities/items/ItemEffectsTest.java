package org.example.entities.items;

import org.example.entities.characters.Warrior;
import org.example.entities.items.consumables.HealthPotion;
import org.example.entities.items.equippables.Sword;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

final class ItemEffectsTest {

    @Test
    void health_potion_heals_up_to_max_and_is_removed_from_inventory() {
        final Warrior player = new Warrior("T", 0, 0);  // maxHealth = 24
        player.takeDamage(10);                           // drop to 14 HP

        final HealthPotion healthPotion = new HealthPotion(); // default tier MEDIUM in your code
        player.getInventory().add(healthPotion);

        final int itemCountBefore = player.getInventory().items().size();
        final boolean usedSuccessfully = player.getInventory().use(healthPotion, player);
        final int itemCountAfter = player.getInventory().items().size();

        assertTrue(usedSuccessfully, "Potion should be usable");
        assertTrue(player.getHealth() >= 14, "Health should increase");
        assertTrue(player.getHealth() <= player.getMaxHealth(), "Health should not exceed max");
        assertEquals(itemCountBefore - 1, itemCountAfter, "Potion should be removed after use");
    }

    @Test
    void equipping_sword_increases_attack_damage() {
        final Warrior player = new Warrior("T", 0, 0);
        final int baseAttackBefore = player.attackDamage(); // 4

        final Sword sword = new Sword(); // default COMMON tier
        player.getInventory().add(sword);
        final boolean usedSuccessfully = player.getInventory().use(sword, player); // equips

        assertTrue(usedSuccessfully, "Sword should be equip-able via inventory.use");
        assertTrue(player.attackDamage() > baseAttackBefore, "Weapon should increase attack damage");
    }
}
