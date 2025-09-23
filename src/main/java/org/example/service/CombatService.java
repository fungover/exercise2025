package org.example.service;

import org.example.entities.Player;
import org.example.entities.Enemy;
import org.example.entities.Item;

public class CombatService {

    // Calculate combat damage with weapon modifiers
    public static int calculateDamage(Player player) {
        int baseDamage = player.getDamage();

        // Check if player has a weapon equipped
        Item weapon = player.getEquippedWeapon();
        if (weapon != null && weapon.isWeapon()) {
            baseDamage += weapon.getEffectValue();
        }

        // Add randomness damage
        double multiplier = 0.8 + (Math.random() * 0.4);
        return (int) (baseDamage * multiplier);
    }

    // Calculate enemy damage
    public static int calculateEnemyDamage(Enemy enemy) {
        int baseDamage = enemy.getDamage();
        return baseDamage;
    }

    // Execute a combat round
    public static String executeCombatRound(Player player, Enemy enemy) {
        StringBuilder result = new StringBuilder();

        // Player attacks first
        int playerDamage = calculateDamage(player);
        enemy.takeDamage(playerDamage);

        result.append("⚔️ You attacked ").append(enemy.getName())
              .append(" for ").append(playerDamage).append(" damage!");

        if (!enemy.isAlive()) {
            result.append("☠️ And you winn! the key is urs 🔑");
            return result.toString();
        }

        // Enemy attacks back
        int enemyDamage = calculateEnemyDamage(enemy);
        player.takeDamage(enemyDamage);

        result.append(" He It attacked you for ").append(enemyDamage).append(" damage!");

        if (!player.isAlive()) {
            result.append(" 💀 You have been defeated!");
        }

        return result.toString();
    }

    // Check if player can use healing potion
    public static boolean canUsePotion(Player player) {
        return player.getHealth() < 100 && player.hasPotion();
    }

    // Use healing potion
    public static String usePotion(Player player) {
        Item potion = player.getPotion();
        if (potion != null && player.getHealth() < 100) {
            int healAmount = potion.getEffectValue();
            player.heal(healAmount);
            player.removeItem(potion);
            return "🩸 You used " + potion.getName() + " and restored " + healAmount + " health!";
        }
        return "❌ You don't have any potions or you're already at full health!";
    }
}