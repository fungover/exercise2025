package org.example.utils;

import org.example.entities.Position;
import org.example.map.Dungeon;

import java.util.Random;

public class GameUtils {

    public static Position randomFloorPosition(Dungeon dungeon, Random random) {
        Position pos;
        do {
            int x = random.nextInt(dungeon.getWidth());
            int y = random.nextInt(dungeon.getHeight());
            pos = new Position(x, y);
        } while (!dungeon.getTile(pos).isWalkable()
                || dungeon.getTile(pos).getEnemy() != null
                || dungeon.getTile(pos).getItem() != null);
        return pos;
    }
}