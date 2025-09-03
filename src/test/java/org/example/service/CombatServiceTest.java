package org.example.service;

import org.example.entities.Enemy;
import org.example.entities.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CombatServiceTest {

    private final CombatService combat = new CombatService();

    @Test
    void attack_reduces_enemy_hp() {
        Player player = new Player("Player");
        Enemy enemy = new Enemy("Enemy", 100, 20);

        CombatResult result = combat.attack(player, enemy);

        assertEquals(80, enemy.getHp(), "Enemy should lose 20 HP");
        assertEquals("Ongoing", result.getStatus(), "Combat should be ongoing");
    }

    @Test
    void attack_kills_enemy () {
        Player player = new Player("Player");
        Enemy enemy = new Enemy("Enemy", 15, 10);
        CombatResult result = combat.attack(player, enemy);

        assertEquals(0, enemy.getHp(), "Enemy HP should not go below 0");
        assertEquals("EnemyDefeated", result.getStatus());
    }

    @Test
    void attack_kills_player () {
        Player player = new Player("Player");
        Enemy enemy = new Enemy("Enemy", 100, 100);
        CombatResult result = combat.attack(player, enemy);

        assertEquals(0, player.getHp(), "Player should be defeated");
        assertEquals("PlayerDefeated", result.getStatus());
    }

    @Test
    void retreat_moves_player_back_to_previous_tile() {
        Player player = new Player("Player");
        player.moveTo(4, 4);

        CombatResult result = combat.retreat(player, 4, 3);

        assertEquals(4, player.getX());
        assertEquals(3, player.getY());
        assertEquals("Retreated", result.getStatus());
    }

    @Test
    void defend_reduces_incoming_damage() {
        Player player = new Player("Player");
        Enemy enemy = new Enemy("Enemy", 50, 20);

        int initialHp = player.getHp();

        CombatResult result = combat.defend(player, enemy);

        assertEquals(initialHp - 10, player.getHp(),
                "Player should only take half damage when defending");
        assertEquals("Ongoing", result.getStatus(), "Combat should be ongoing after defending");
    }
}
