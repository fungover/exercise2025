package org.example.entities.enemies;

import org.example.entities.Player;
import org.example.entities.Position;
import org.example.service.LootDropOutcome;
import org.example.service.LootService;

public class Orc extends Enemy {

    public Orc(Position position) {
        super("Orc Warrior", 40, 12, position);
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
            return ">The Orc Warrior swings its axe at you for " + actualDamage + " damage! (reduced from " + originalDamage + " by armor)";
        } else {
            return ">The Orc Warrior swings its axe at you for " + actualDamage + " damage!";
        }
    }

    @Override
    public void dropLoot(Player player, LootService lootService) {
        LootDropOutcome outcome = lootService.tryDropRandomLoot(player);
        System.out.println(outcome.getMessage());
    }
}
