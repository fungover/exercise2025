package org.example;

import entities.Player;
import entities.Witch;
import map.DungeonMap;
import map.TileType;
import org.junit.jupiter.api.Test;
import service.Combat;
import utils.Position;

import static org.junit.jupiter.api.Assertions.*;

class CombatTest {

    @Test
    void playerAttackHere_damagesAndRemovesEnemyOnDeath() {
        DungeonMap map = new DungeonMap(6, 6);
        map.getTile(3,3).setType(TileType.FLOOR);

        Player player = new Player();
        player.setPosition(new Position(3,3));

        Witch w = new Witch(15, 3, new Position(3,3));
        map.getTile(3,3).setEnemy(w);

        Combat combat = new Combat(player, map);

        assertTrue(combat.playerAttackHere());
        assertEquals(5, w.getHealth());
        assertNotNull(map.getTile(3,3).getEnemy());

        assertTrue(combat.playerAttackHere());
        assertEquals(0, w.getHealth());
        assertNull(map.getTile(3,3).getEnemy());
    }

    @Test
    void enemyTurn_damagesPlayerIfEnemyOnSameTile() {
        DungeonMap map = new DungeonMap(5, 5);
        map.getTile(2,2).setType(TileType.FLOOR);

        Player player = new Player();
        player.setPosition(new Position(2,2));
        int startHp = player.getHealth();

        Witch w = new Witch(20, 6, new Position(2,2));
        map.getTile(2,2).setEnemy(w);

        Combat combat = new Combat(player, map);
        combat.enemyTurn();

        assertEquals(startHp - 6, player.getHealth());
    }
}
