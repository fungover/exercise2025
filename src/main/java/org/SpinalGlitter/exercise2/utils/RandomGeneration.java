package org.SpinalGlitter.exercise2.utils;

import org.SpinalGlitter.exercise2.entities.*;
import org.SpinalGlitter.exercise2.map.DungeonMap;

import java.util.*;

public final class RandomGeneration {
    private RandomGeneration() {
    }


    public static Map<Position, Potion> placePotions(DungeonMap map, int count, Position avoid, Random rng) {
        Map<Position, Potion> out = new HashMap<>();
        int W = map.getWidth(), H = map.getHeight();
        int tries = 0;
        int maxTries = Math.max(1, count) * 200;

        while (out.size() < count && tries++ < maxTries) {
            int x = 1 + rng.nextInt(Math.max(1, W - 2));
            int y = 1 + rng.nextInt(Math.max(1, H - 2));
            Position p = new Position(x, y);

            if (!p.equals(avoid) && map.canMoveTo(p) && !out.containsKey(p)) {
                out.put(p, new Potion(p));
            }
        }
        return out;
    }

    // Random placing of walls
    public static Set<Position> placeWalls(DungeonMap map, Set<Position> occupied, int count, Position avoid, Random rng) {
        int W = map.getWidth(), H = map.getHeight();
        int placed = 0, tries = 0, maxTries = Math.max(1, count) * 400;
        // changes maybe delete
        Set<Position> walls = new HashSet<>();

        while (placed < count && tries++ < maxTries) {
            int x = 1 + rng.nextInt(Math.max(1, W - 2));
            int y = 1 + rng.nextInt(Math.max(1, H - 2));
            Position p = new Position(x, y);

            // put only walls on empty spaces and not on the player start position
            if (!p.equals(avoid) && !occupied.contains(p) && map.isFloor(p)) {
                walls.add(p);
                map.setWall(p);
                placed++;
            }
        }
        return walls; // return is just for testing purposes
    }

    // Random placing of enemies
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
                // 50/50 Goblin or Skeleton
                Enemy e = (rng.nextBoolean()) ? new Goblin(p) : new Skeleton(p);
                out.put(p, e);
            }
        }
        return out;
    }

    // Place sword
    public static Map<Position, Sword> placeSwords(
            DungeonMap map,
            int count,
            Position avoid,
            Random rng,
            Set<Position> occupied) {

        Map<Position, Sword> out = new HashMap<>();
        int W = map.getWidth(), H = map.getHeight();
        int tries = 0;
        int maxTries = Math.max(1, count) * 400;

        while (out.size() < count && tries++ < maxTries) {
            int x = 1 + rng.nextInt(Math.max(1, W - 2));
            int y = 1 + rng.nextInt(Math.max(1, H - 2));
            Position p = new Position(x, y);

            if (!p.equals(avoid)
                    && map.canMoveTo(p)
                    && !out.containsKey(p)
                    && !occupied.contains(p)) {
                out.put(p, new Sword(p));
                occupied.add(p);
            }
        }
        return out;
    }
}
