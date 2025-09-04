package org.SpinalGlitter.exercise2.utils;

import org.SpinalGlitter.exercise2.entities.*;
import org.SpinalGlitter.exercise2.map.DungeonMap;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class RandomGenerationTest {

    @Test
    void placeItems() {
        DungeonMap map = new DungeonMap(20, 20);
        Player player = new Player("Hero");
        Random rng = new Random(42);

        Set<Position> occupied = new HashSet<>();
        occupied.add(player.getPosition());

        Map<Position, Potion> potions = RandomGeneration.placePotions(map, 5, player.getPosition(), rng);
        occupied.addAll(potions.keySet());
        Map<Position, Enemy> enemies = RandomGeneration.placeEnemies(map, 5, player.getPosition(), rng);
        occupied.addAll(enemies.keySet());
        Set<Position> walls = RandomGeneration.placeWalls(map, occupied, 10, player.getPosition(), rng);
        occupied.addAll(walls);


        Map<Position, Sword> sword = RandomGeneration.placeSwords(map, 1, player.getPosition(), rng, occupied);
        occupied.addAll(sword.keySet());

        assert potions.size() == 5 : "Expected 5 potions, and got " + potions.size();
        assert enemies.size() == 5 : "Expected 5 enemies, and got " + enemies.size();
        assert walls.size() == 10 : "Expected 10 walls, and got " + walls.size();
        assert occupied.size() == 21 : "Expected 11 occupied positions, and got " + occupied.size();

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