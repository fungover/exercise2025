package org.example.game;

import org.example.entities.*;
import org.example.map.Dungeon;
import org.example.map.MapGen;
import org.example.service.*;
import org.example.utils.GameUtils;


import java.util.Random;

public class Game {
     static void main(String[] args) {
        int width = 12;
        int height = 12;
        Random random = new Random();

        MapGen generator = new MapGen();
        Dungeon dungeon = generator.generate(width, height);

        Player hero = new Player("Hero", 30, 5, GameUtils.randomFloorPosition(dungeon, random));

        EnemySpawner enemySpawner = new EnemySpawner(dungeon, random);
        enemySpawner.spawnEnemies();

        ItemSpawner itemSpawner = new ItemSpawner(dungeon, random);
        itemSpawner.spawnItem(6);

        GameLoop gameLoop = new GameLoop(dungeon, hero);
        gameLoop.run();
    }
}
