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
        int attempts = 0;
        int cap = grid.getWidth() * grid.getHeight() * 2;

        while (attempts++ < cap) {
            x = random.nextInt(grid.getWidth());
            y = random.nextInt(grid.getHeight());

            occupied = grid.getTiles(x, y).isWall()
                    || (player.getX() == x && player.getY() == y)
                    || Helpers.containsEnemyAt(x, y, enemies)
                    || Helpers.containsItemAt(x, y, items);
            if (!occupied) {
                return new int[]{x, y};
            }
        }
        // Fallback scanning
        for (int yy = 0; yy < grid.getHeight(); yy++) {
            for (int xx = 0; xx < grid.getWidth(); xx++) {
                boolean fallbackOccupied = grid.getTiles(xx, yy).isWall()
                        || (player.getX() == xx && player.getY() == yy)
                        || Helpers.containsEnemyAt(xx, yy, enemies)
                        || Helpers.containsItemAt(xx, yy, items);
                if (!fallbackOccupied) {
                    return new int[]{xx, yy};
                }
            }
        }
        throw new IllegalStateException("Unable to find a free position");
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

