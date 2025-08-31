package org.example.entities;

import org.example.service.LootService;

public class Goblin extends Enemy {
    private static boolean swordDropped = false;

    public Goblin(Position position) {
        super("Goblin", 25, 8, position);
    }

    @Override
    public void attack(Player player) {
        player.takeDamage(getDamage());
        System.out.println(getAttackMessage());
    }

    @Override
    public String getAttackMessage() {
        return ">The Goblin stabs you with its sword for " + getDamage() + " damage!";
    }

    @Override
    public void dropLoot(Player player, LootService lootService) {
        lootService.tryDropRandomLoot(player);
    }
}