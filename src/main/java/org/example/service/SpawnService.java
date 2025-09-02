package org.example.service;

import org.example.entities.enemies.Enemy;
import org.example.entities.enemies.Goblin;
import org.example.entities.items.Weapon;
import org.example.entities.items.weapons.WeaponLibrary;
import org.example.map.DungeonGrid;
import org.example.map.Tile;
import org.example.utils.RandomUtils;

import java.util.Random;
import java.util.function.Supplier;

public class SpawnService {
    public void spawnEnemies(DungeonGrid grid, int count, Supplier<Enemy> enemySupplier) {
        for (int i = 0; i < count; i++) {
            Enemy enemy = enemySupplier.get();
            int x, y;
            do {
                x = RandomUtils.getRandomNumber(0, grid.getWidth() - 1);
                y = RandomUtils.getRandomNumber(0, grid.getHeight() - 1);
            } while (grid.getTiles()[x][y].getType() != Tile.TileType.FLOOR
                    || grid.getTiles()[x][y].getEnemy() != null);

            grid.getTiles()[x][y].setEnemy(enemy);
        }
    }

    public void spawnWeapons(DungeonGrid grid, int count) {
        for (int i = 0; i < count; i++) {
            Weapon weapon = WeaponLibrary.weapons.get(RandomUtils.getRandomNumber(0, WeaponLibrary.weapons.size() - 1));

            int x, y;
            do {
                x = RandomUtils.getRandomNumber(0, grid.getWidth() - 1);
                y = RandomUtils.getRandomNumber(0, grid.getHeight() - 1);
            } while (grid.getTiles()[x][y].getType() != Tile.TileType.FLOOR
                    || !grid.getTiles()[x][y].getItems().isEmpty()
                    || grid.getTiles()[x][y].getEnemy() != null);

            grid.getTiles()[x][y].addItem(weapon);
        }
    }
}
