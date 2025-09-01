package org.example.entities.enemies;

import org.example.entities.Player;
import org.example.entities.Position;
import org.example.service.LootDropOutcome;
import org.example.service.LootService;

public class Dragon extends Enemy implements Boss {
    private boolean hasUsedSpecialAttack = false;

    public Dragon(Position position) {
        super("Ancient Dragon", 150, 20, position);
    }

    @Override
    public void attack(Player player) {
        if (hasSpecialAttack()) {
            performSpecialAttack(player);
        } else {
            int actualDamage = Math.max(1, getDamage() - player.getDefense());
            player.takeDamage(actualDamage, getDamage());
            System.out.println(getAttackMessage(actualDamage, getDamage()));
        }
    }

    @Override
    public void performSpecialAttack(Player player) {
        System.out.println("The Ancient Dragon breathes scorching fire!");
        player.takeDamage(50, 50);
        hasUsedSpecialAttack = true;
    }

    @Override
    public boolean hasSpecialAttack() {
        return !hasUsedSpecialAttack && getHealth() < (getMaxHealth() / 2);
    }

    @Override
    public String getDefeatMessage() {
        return "The might Ancient Dragon has fallen! You have conquered the dungeon!";
    }

    @Override
    public String getAttackMessage(int actualDamage, int originalDamage) {
        if (actualDamage < originalDamage) {
            return "The Dragon slashes you with its claws for " + actualDamage + " damage! (reduced from " + originalDamage + " by armor)";
        } else {
            return "The Dragon slahes you with its claws for " + actualDamage + " damage!";
        }
    }

    @Override
    public void dropLoot(Player player, LootService lootService) {
        System.out.println(getDefeatMessage());
        LootDropOutcome outcome = lootService.tryDropRandomLoot(player);
        System.out.println(outcome.getMessage());
    }
}
