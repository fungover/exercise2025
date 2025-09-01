package org.SpinalGlitter.exercise2.utils;

import org.SpinalGlitter.exercise2.entities.Potion;
import org.SpinalGlitter.exercise2.entities.Position;
import org.SpinalGlitter.exercise2.map.DungeonMap;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public final class RandomGeneration {
    private RandomGeneration() {}

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
}
