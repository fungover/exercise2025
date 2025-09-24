package service;

import entities.Enemy;
import entities.Goblin;
import entities.HealingPotion;
import entities.Witch;
import map.DungeonMap;
import map.Tile;
import utils.Position;

import java.util.Random;

public class Spawner {
    private final DungeonMap dungeonMap;
    private final Random random = new Random();

    public Spawner(DungeonMap dungeonMap) {
        this.dungeonMap = dungeonMap;
    }

    public int spawnRandomEnemies(int count) {
        int placed = 0;
        for (int i = 0; i < count; i++) {
            Position position = dungeonMap.randomEmptyFloor(200);
            if (position == null) break;

            Enemy enemy = randomEnemyAt(position);

            Tile tile = dungeonMap.getTile(position.getX(), position.getY());
            if (tile.isEmpty()) {
                tile.setEnemy(enemy);
                enemy.setPosition(position);
                placed++;
            }
        }
        return placed;
    }

    private Enemy randomEnemyAt(Position pos) {
        double r = random.nextDouble();
        if (r < 0.5) {
            int hp = getRandom(15, 25);
            int dmg = getRandom(3, 6);
            return new Goblin(hp, dmg, pos);
        } else {
            int hp = getRandom(24, 40);
            int dmg = getRandom(4, 8);
            return new Witch(hp, dmg, pos);
        }
    }

    public int spawnRandomItems(int count) {
        int placed = 0;
        for (int i = 0; i < count; i++) {
            Position pos = dungeonMap.randomEmptyFloor(200);
            if (pos == null) {
                break;
            }

            boolean big = random.nextDouble() < 0.4;
            int heal = big ? getRandom(25, 35) : getRandom(12, 20);
            String name = big ? "Big Potion" : "Small Potion";

            Tile tile = dungeonMap.getTile(pos.getX(), pos.getY());
            if (tile.isEmpty()) {
                tile.setItem(new HealingPotion(name, "potion", heal));
                placed++;
            }
        }
        return placed;
    }

    private int getRandom(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }
}
