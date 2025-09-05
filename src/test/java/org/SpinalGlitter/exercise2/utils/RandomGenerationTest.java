package org.SpinalGlitter.exercise2.utils;

import org.SpinalGlitter.exercise2.entities.*;
import org.SpinalGlitter.exercise2.map.DungeonMap;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class RandomGenerationTest {

    @Test
    void placeItems() {
        DungeonMap map = new DungeonMap(20, 20);
        RandomGeneration random = new RandomGeneration(map);
        Player player = new Player("Hero");
        Random rng = new Random(42);

        Set<Position> occupied = new HashSet<>();
        occupied.add(player.getPosition());

        Map<Position, Potion> potions = random.placePotions(5, player.getPosition(), rng);
        occupied.addAll(potions.keySet());
        Map<Position, Enemy> enemies = random.placeEnemies(5, player.getPosition(), rng);
        occupied.addAll(enemies.keySet());
        Set<Position> walls = random.placeWalls(occupied, 10, player.getPosition(), rng);
        occupied.addAll(walls);


        Set<Position> occupiedBeforeSword = new HashSet<>(occupied);
        Map<Position, Sword> sword = random.placeSwords(1, player.getPosition(), rng, occupied);
        occupied.addAll(sword.keySet());

        assertEquals(5, potions.size(), "Expected 5 potions");
        assertEquals(5, enemies.size(), "Expected 5 enemies");
        assertEquals(10, walls.size(), "Expected 10 walls");
        assertEquals(1, sword.size(), "Expected 1 sword");
        assertTrue(Collections.disjoint(sword.keySet(), occupiedBeforeSword), "Sword must not overlap prior occupied");
        assertEquals(occupiedBeforeSword.size() + sword.size(), occupied.size(), "Occupied should increase only by sword count");
    }

    @Test
    void generateMapAndPlayer() {
        DungeonMap map = new DungeonMap(10, 10);
        Player player = new Player("Hero");

        assertDoesNotThrow(() ->
                map.printMap(player.getPosition(), Map.of(), Map.of(), Map.of())
        );
    }
}