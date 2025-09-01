package org.example;

import org.example.map.Dungeon;
import org.example.service.EnemyService;
import org.example.utils.RandomGenerator;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EnemyServiceTest {

    //TEST 1: Testing that placing enemies in "Dungeon Entrance" results in exactly 4 enemies being placed.
    @Test
    void testDungeonEntrancePlacesCorrectNumberOfEnemies() {
        Dungeon dungeon = new Dungeon(8, 10);
        Random mockRandom = new Random(12345);
        RandomGenerator testGenerator = new RandomGenerator(mockRandom);
        EnemyService enemyService = new EnemyService(testGenerator);

        enemyService.placeEnemiesForRoom("Dungeon Entrance", dungeon);

        assertEquals(4, enemyService.getEnemies().size());
        assertTrue(enemyService.hasEnemies());
    }

    //TEST 2: Testing that placing enemies in "Treasure Room" results in exactly 3 enemies being placed.
    @Test
    void testTreasureRoomPlacesCorrectNumberOfEnemies() {
        Dungeon dungeon = new Dungeon(6, 8);
        Random mockRandom = new Random(12345);
        RandomGenerator testGenerator = new RandomGenerator(mockRandom);
        EnemyService enemyService = new EnemyService(testGenerator);

        enemyService.placeEnemiesForRoom("Treasure Room", dungeon);

        assertEquals(3, enemyService.getEnemies().size());
        assertTrue(enemyService.hasEnemies());
    }
}
