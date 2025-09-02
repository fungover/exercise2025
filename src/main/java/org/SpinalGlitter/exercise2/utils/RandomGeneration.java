package org.SpinalGlitter.exercise2.utils;

import org.SpinalGlitter.exercise2.entities.*;
import org.SpinalGlitter.exercise2.map.DungeonMap;
import org.SpinalGlitter.exercise2.entities.Sword;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public final class RandomGeneration {
    private RandomGeneration() {
    }

    public static Map<Position, Potion> placePotions(DungeonMap map, int count, Position avoid, Random rng) {
        Map<Position, Potion> out = new HashMap<>();
        int W = map.getWidth(), H = map.getHeight();
        int tries = 0;
        int maxTries = Math.max(1, count) * 200;

        while (out.size() < count && tries++ < maxTries) {
            int x = 1 + rng.nextInt(Math.max(1, W - 2)); // undvik ytterväggar
            int y = 1 + rng.nextInt(Math.max(1, H - 2));
            Position p = new Position(x, y);

            if (!p.equals(avoid) && map.canMoveTo(p) && !out.containsKey(p)) {
                out.put(p, new Potion(p));
            }
        }
        return out;
    }

    // NYTT: slumpmässiga väggar som hinder (muterar kartan)
    public static void placeWalls(DungeonMap map, int count, Position avoid, Random rng) {
        int W = map.getWidth(), H = map.getHeight();
        int placed = 0, tries = 0, maxTries = Math.max(1, count) * 400;

        while (placed < count && tries++ < maxTries) {
            int x = 1 + rng.nextInt(Math.max(1, W - 2));
            int y = 1 + rng.nextInt(Math.max(1, H - 2));
            Position p = new Position(x, y);

            // lägg endast vägg på golv, ej start, och lämna kvar walkability runt start hyfsat
            if (!p.equals(avoid) && map.isFloor(p)) {
                map.setWall(p);
                placed++;
            }
        }
    }

    // NYTT: slumpmässiga fiender
    public static Map<Position, Enemy> placeEnemies(DungeonMap map, int count, Position avoid, Random rng) {
        Map<Position, Enemy> out = new HashMap<>();
        int W = map.getWidth(), H = map.getHeight();
        int tries = 0;
        int maxTries = Math.max(1, count) * 400;

        while (out.size() < count && tries++ < maxTries) {
            int x = 1 + rng.nextInt(Math.max(1, W - 2));
            int y = 1 + rng.nextInt(Math.max(1, H - 2));
            Position p = new Position(x, y);

            if (!p.equals(avoid) && map.canMoveTo(p) && !out.containsKey(p)) {
                // 50/50 Goblin eller Skeleton
                Enemy e = (rng.nextBoolean()) ? new Goblin(p) : new Skeleton(p);
                out.put(p, e);
            }
        }
        return out;
    }

    // NYTT: placera svärd
    public static Map<Position, Sword> placeSwords(DungeonMap map, int count, Position avoid, Random rng) {
        Map<Position, Sword> out = new HashMap<>();
        int W = map.getWidth(), H = map.getHeight();
        int tries = 0;
        int maxTries = Math.max(1, count) * 200;

        while (out.size() < count && tries++ < maxTries) {
            int x = 1 + rng.nextInt(Math.max(1, W - 2));
            int y = 1 + rng.nextInt(Math.max(1, H - 2));
            Position p = new Position(x, y);

            if (!p.equals(avoid) && map.canMoveTo(p) && !out.containsKey(p)) {
                out.put(p, new Sword(p));
            }
        }
        return out;
    }
}
