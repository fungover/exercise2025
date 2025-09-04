package org.example.utils;
import org.example.entities.Character;
import java.util.List;


import java.util.Random;

public class RandomGeneration {
    private static final Random random = new Random();

    public static int[] getRandomPosition(
            org.example.map.DungeonGrid grid,
            Character player,
            List<Character> enemies,
            List<ItemOnMap> items) {
        int x, y;
        boolean occupied;

        do {
            x = random.nextInt(grid.getWidth());
            y = random.nextInt(grid.getHeight());

            occupied = grid.getTiles(x, y).isWall()
                    || (player.getX() == x && player.getY() == y)
                    || containsEnemyAt(x, y, enemies)
                    || containsItemAt(x, y, items);
        } while (occupied);
        return new int[]{x, y};

    }
    private static boolean containsEnemyAt(int x, int y, List<Character> enemies) {
        for (Character e : enemies) {
            if (e.getX() == x && e.getY() == y) return true;
            }
        return false;
        }
        private static boolean containsItemAt(int x, int y, List<ItemOnMap> items) {
            for (ItemOnMap i : items) {
                if (i.x == x && i.y == y) return true;
            }
            return false;
        }
    }

