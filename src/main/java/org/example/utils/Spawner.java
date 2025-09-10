package org.example.utils;

import org.example.entities.enemies.Enemy;
import org.example.entities.enemies.Orc;
import org.example.entities.enemies.Troll;
import org.example.entities.items.Item;
import org.example.entities.items.Potion;
import org.example.entities.items.Weapon;
import org.example.map.Dungeon;
import org.example.map.Tile;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Spawner {

    private static final Random random = new Random();

    public static void spawnAll(Dungeon dungeon) {
        spawnEnemies(dungeon);
        spawnItems(dungeon);
    }

    private static void spawnEnemies(Dungeon dungeon) {

        List<Enemy> enemies = List.of(
                new Troll(0, 0),
                new Orc(0, 0)
        );

        List<Tile> emptyTiles = getWalkableTiles(dungeon);

        for (Enemy enemy : enemies) {
            Tile tile = emptyTiles.remove(random.nextInt(emptyTiles.size()));
            tile.setEnemy(enemy);
            enemy.moveTo(tile.getRow(), tile.getCol());
        }
    }

    private static void spawnItems(Dungeon dungeon) {
        // Skapa items
        List<Item> items = List.of(
                new Potion("Small Potion", 20),
                new Potion("Large Potion", 50),
                new Weapon("Wooden Sword", 10),
                new Weapon("Fire Sword", 20)
        );

        // Hämta alla walkable tiles
        List<Tile> emptyTiles = getWalkableTiles(dungeon);

        // Placera items slumpmässigt
        for (Item item : items) {
            Tile tile = emptyTiles.remove(random.nextInt(emptyTiles.size()));
            tile.setItem(item);
        }
    }


    private static List<Tile> getWalkableTiles(Dungeon dungeon) {
        return IntStream.range(0, dungeon.getRows())
                .boxed()
                .flatMap(row -> IntStream.range(0, dungeon.getCols())
                        .mapToObj(col -> dungeon.getTile(row, col)))
                .filter(Tile::isWalkable)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
