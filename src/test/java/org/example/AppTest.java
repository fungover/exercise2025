package org.example;

import org.example.entities.Player;
import org.example.entities.enemies.Enemy;
import org.example.entities.items.Item;
import org.example.entities.items.Weapon;
import org.example.game.Game;
import org.example.map.Dungeon;
import org.example.map.Tile;
import org.example.service.MovementLogic;
import org.example.service.Combat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static org.assertj.core.api.Assertions.assertThat;


//Tests for
//
//Movement logic - Check
//
//Combat calculations - Check
//
//Item effects - check
//
//Map generation (basic validation)
class AppTest {

    @DisplayName("Checking if player walks in correct direction")
    @Test
    void playerGoToRight() {

        Dungeon dungeon = new Dungeon(2, 2);
        Player player = new Player("TestPlayer");
        MovementLogic logic = new MovementLogic();

        logic.movePlayer(player, "right", dungeon);

        assertThat(player.getRow()).isEqualTo(0);
        assertThat(player.getCol()).isEqualTo(1);
    }

    @Test
    void playerGoesUp() {
        Dungeon dungeon = new Dungeon(2, 2);
        Player player = new Player("TestPlayer");
        MovementLogic logic = new MovementLogic();

        player.moveTo(2, 0);
        logic.movePlayer(player, "up", dungeon);

        assertThat(player.getRow()).isEqualTo(1);
        assertThat(player.getCol()).isEqualTo(0);
    }

    @Test
    void playerGoesLeft() {
        Dungeon dungeon = new Dungeon(2, 2);
        Player player = new Player("TestPlayer");
        MovementLogic logic = new MovementLogic();

        player.moveTo(1, 1);
        logic.movePlayer(player, "left", dungeon);

        assertThat(player.getRow()).isEqualTo(1);
        assertThat(player.getCol()).isEqualTo(0);
    }

    @Test
    void playerGoesDown() {
        Dungeon dungeon = new Dungeon(2, 2);
        Player player = new Player("TestPlayer");
        MovementLogic logic = new MovementLogic();

        player.moveTo(0, 0);
        logic.movePlayer(player, "down", dungeon);

        assertThat(player.getRow()).isEqualTo(1);
        assertThat(player.getCol()).isEqualTo(0);
    }

    // Combat testing - Damage, attack

    void attack(Player player, Enemy enemy, Weapon weapon) {
        int playerDamage = weapon.getDamage();
        enemy.takeDamage(playerDamage);

        if (enemy.isAlive()) {
            int enemyDamage = enemy.getDamage();
            player.setHealth(player.getHealth() - enemyDamage);
        }
    }

    @Test
    void playerAttacksEnemyWithSwordAndTrollAttacksBack() {

        Player player = new Player("Taru");
        Weapon sword = new Weapon("Wooden Sword", 10);
        player.addItem(sword);

        Enemy enemy = new Enemy("Troll", 80, 20, 0, 0);

        attack(player, enemy, sword);

        assertThat(enemy.getHealth()).isEqualTo(70);
        assertThat(player.getHealth()).isEqualTo(80);
    }

    @Test
    void enemyDies() {

        Player player = new Player("Hundis Pilotosh");
        Weapon sword = new Weapon("Fire Sword", 40);
        player.addItem(sword);

        Enemy enemy = new Enemy("Orc", 40, 10, 0, 0);

        attack(player, enemy, sword);

        assertThat(enemy.getHealth()).isEqualTo(0);
        assertThat(enemy.isAlive()).isFalse();
        assertThat(player.getHealth()).isEqualTo(100);
    }

    // tests for map generation

    @Test
    void dungeonBasicMapGeneration() {
        Dungeon dungeon = new Dungeon(5, 5);

        assertThat(dungeon.getRows()).isEqualTo(5);
        assertThat(dungeon.getCols()).isEqualTo(5);

        for (int i = 0; i < dungeon.getRows(); i++) {
            for (int j = 0; j < dungeon.getCols(); j++) {
                Tile tile = dungeon.getTile(i, j);
                assertThat(tile).isNotNull();
                assertThat(tile.getTileType()).isEqualTo("Empty");
            }
        }
    }
}