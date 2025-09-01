package org.example;

import org.example.entities.Position;
import org.example.entities.enemies.Boss;
import org.example.entities.enemies.Enemy;
import org.example.map.Dungeon;
import org.example.service.EnemyService;
import org.example.utils.RandomGenerator;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

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

    //TEST 3: Testing that placing enemies in "Dragon's Lair" results in exactly 1 boss being placed.
    @Test
    void testDragonLairPlacesBoss() {
        Dungeon dungeon = new Dungeon(10, 12);
        Random mockRandom = new Random(12345);
        RandomGenerator testGenerator = new RandomGenerator(mockRandom);
        EnemyService enemyService = new EnemyService(testGenerator);

        enemyService.placeEnemiesForRoom("Dragon's Lair", dungeon);

        assertEquals(1, enemyService.getEnemies().size());
        Enemy placedEnemy = enemyService.getEnemies().get(0);
        assertInstanceOf(Boss.class, placedEnemy);
        assertEquals("Ancient Dragon", placedEnemy.getName());
    }

    //TEST 4: Testing that enemies do not spawn on the same position in "Dungeon Entrance".
    @Test
    void testEnemiesSpawnOnUniquePositions() {

        Dungeon dungeon = new Dungeon(8, 10);
        Random mockRandom = new Random(12345);
        RandomGenerator testGenerator = new RandomGenerator(mockRandom);
        EnemyService enemyService = new EnemyService(testGenerator);

        enemyService.placeEnemiesForRoom("Dungeon Entrance", dungeon);
        List<Enemy> enemies = enemyService.getEnemies(); // Should be 4 enemies

        assertEquals(4, enemies.size()); // Ensure we have 4 enemies to check

        List<Position> positions = enemies.stream() // Get positions of all enemies
                .map(Enemy::getPosition) // Map each enemy to its position
                .toList(); // Collect positions into a list

        Set<Position> uniquePositions = new HashSet<>(positions); // Use a set to filter out duplicates
        assertEquals(positions.size(), uniquePositions.size(),
                "Enemies should not spawn on the same position"); // Ensure all positions are unique
    }
}
