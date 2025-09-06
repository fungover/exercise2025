package org.example.service;

import org.example.entities.Enemy;
import org.example.entities.Position;
import org.example.utils.GameUtils;
import org.example.map.Dungeon;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EnemySpawner {
    private final Random random;
    private final Dungeon dungeon;

    public EnemySpawner(Dungeon dungeon, Random random) {
        this.dungeon = dungeon;
        this.random = random;
    }

    public List<Enemy> spawnEnemies() {
        List<Enemy> enemies = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            Enemy troll = spawnTroll();
            if (troll != null) enemies.add(troll);
        }
        for (int i = 0; i < 3; i++) {
            Enemy witch = spawnWitch();
            if (witch != null) enemies.add(witch);
        }

        Position treasurePos = GameUtils.randomFloorPosition(dungeon, random);
        enemies.add(spawnDragon(treasurePos));

        return enemies;
    }

    private Enemy spawnTroll() {
        try {
            Position pos = GameUtils.randomFloorPosition(dungeon, random);
            Enemy troll = new Enemy("Troll", 10 + random.nextInt(10), 3 + random.nextInt(3), pos) {
                @Override
                public String getType() {
                    return "Troll";
                }
            };
            dungeon.getTile(pos).setEnemy(troll);
            return troll;
        } catch (IllegalStateException e) {
            System.out.println("No space to spawn a Troll!");
            return null;
        }
    }

    private Enemy spawnWitch() {
            try {
                Position pos = GameUtils.randomFloorPosition(dungeon, random);
                Enemy witch = new Enemy("Witch", 8 + random.nextInt(8), 4 + random.nextInt(2), pos) {
                    @Override
                    public String getType() {
                        return "Witch";
                    }
                };
                dungeon.getTile(pos).setEnemy(witch);
                return witch;
            } catch (IllegalStateException e) {
                System.out.println("No space to spawn a Witch!");
                return null;
            }
        }

    private Enemy spawnDragon(Position pos) {
        Enemy dragon = new Enemy("Dragon", 30, 8, pos) {
            @Override public String getType() { return "Dragon"; }
        };
        dungeon.getTile(pos).setEnemy(dragon);
        return dragon;
    }
}