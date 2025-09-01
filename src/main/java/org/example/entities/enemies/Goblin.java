package org.example.entities.enemies;

import org.example.entities.Player;
import org.example.entities.Position;
import org.example.service.LootDropOutcome;
import org.example.service.LootService;

public class Goblin extends Enemy {
    private static boolean swordDropped = false;

    public Goblin(Position position) {
        super("Goblin", 25, 8, position);
    }

    @Override
    public void attack(Player player) {
        int actualDamage = Math.max(1, getDamage() - player.getDefense());
        player.takeDamage(actualDamage, getDamage());
        System.out.println(getAttackMessage(actualDamage, getDamage()));
    }

    @Override
    public String getAttackMessage(int actualDamage, int originalDamage) {
        if (actualDamage < originalDamage) {
            return "The Goblin stabs you for " + actualDamage + " damage! (reduced from " + originalDamage + " by armor)";
        } else {
            return "The Goblin stabs you for " + actualDamage + " damage!";
        }
    }

    @Override
    public void dropLoot(Player player, LootService lootService) {
        LootDropOutcome outcome = lootService.tryDropRandomLoot(player);
        System.out.println(outcome.getMessage());
    }
}