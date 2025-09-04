package org.SpinalGlitter.exercise2.service;

import org.SpinalGlitter.exercise2.entities.*;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

class CombatServiceTest {

    @Test
    void playerDefeatsGoblinAndBattleCountIncreases() {

        Position pos = new Position(1, 1);
        Goblin goblin = new Goblin(pos);
        Player player = new Player("Hero");

        Map<Position, Enemy> enemies = new HashMap<>();
        enemies.put(pos, goblin);


        // Print 1 so the player attacks three times and defeats the goblin
        Scanner scanner = new Scanner("1\n1\n1\n");


        CombatService combatService = new CombatService(enemies, scanner);

        // Act
        combatService.startCombat(player, goblin);

        // Assert
        assertTrue(goblin.getHp() <= 0, "Enemy should be defeated");
        assertTrue(combatService.haveWon() || !goblin.isAlive());
        assertEquals(1, combatService.getBattleCount(), "Battle count should be 1 after defeating the goblin");

    }

    @Test
    void playerDefeatsSkeletonAndBattleCountIncreases() {

        Position pos = new Position(1, 1);
        Skeleton skeleton = new Skeleton(pos);
        Player player = new Player("Hero");

        Map<Position, Enemy> enemies = new HashMap<>();
        enemies.put(pos, skeleton);

        // Print 1 so the player attacks three times and defeats the skeleton
        Scanner scanner = new Scanner("1\n1\n1\n");


        CombatService combatService = new CombatService(enemies, scanner);

        // Act
        combatService.startCombat(player, skeleton);

        // Assert
        assertTrue(skeleton.getHp() <= 0, "Enemy should be defeated");
        assertTrue(combatService.haveWon() || !skeleton.isAlive());
        assertEquals(1, combatService.getBattleCount(), "Battle count should be 1 after defeating the skeleton");

    }

    @Test
    void playerCanHealDuringCombat() {
        Position pos = new Position(1, 1);
        Goblin goblin = new Goblin(pos);
        Player player = new Player("Hero");
        player.getInventory().addItem(new Potion(null));

        player.takeDamage(20);

        Map<Position, Enemy> enemies = new HashMap<>();
        enemies.put(pos, goblin);

        // Third input = "2" (heal), then we attack until the enemy is dead
        Scanner scanner = new Scanner("1\n1\n2\n1");

        CombatService combatService = new CombatService(enemies, scanner);
        combatService.startCombat(player, goblin);

        assertEquals(79, player.getCurrentHealth(), "Player should have healed");
    }

    @Test
    void playerCanDieInCombat() {
        Position pos = new Position(1, 1);
        Goblin goblin = new Goblin(pos);
        Player player = new Player("Hero");

        player.takeDamage(93);

        Map<Position, Enemy> enemies = new HashMap<>();
        enemies.put(pos, goblin);

        Scanner scanner = new Scanner("1");

        CombatService combatService = new CombatService(enemies, scanner);
        combatService.startCombat(player, goblin);

        assertFalse(player.isAlive());
    }

    @Test
    void playerHasDoubleDamageWithSword() {
        Position pos = new Position(1, 1);
        Goblin goblin = new Goblin(pos);
        goblin.takeDamage(10);
        Player player = new Player("Hero");
        player.getInventory().addItem(new Sword(null));
        player.setDamage();

        Map<Position, Enemy> enemies = new HashMap<>();
        enemies.put(pos, goblin);

        Scanner scanner = new Scanner("1");

        CombatService combatService = new CombatService(enemies, scanner);
        combatService.startCombat(player, goblin);


        assertTrue(combatService.haveWon() || !goblin.isAlive());
    }

    @Test
    void playerLosesDamageWithoutSword() {

        Player player = new Player("Hero");
        player.getInventory().addItem(new Sword(null));
        player.setDamage();

        assertEquals(20, player.getDamage(), "Damage should be 20 with sword");

        player.getInventory().removeWeapon();
        player.setDamage();

        assertEquals(10, player.getDamage(), "Damage should be 10 after removing sword");



    }
}


