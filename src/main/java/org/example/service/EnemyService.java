package org.example.service;

import org.example.entities.Enemy;
import org.example.entities.Goblin;
import org.example.entities.Position;
import org.example.map.Dungeon;
import org.example.utils.RandomGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EnemyService {
    private final List<Enemy> enemies;
    private final Random random;

    public EnemyService() {
        this.enemies = new ArrayList<>();
        this.random = new Random();
    }

    public void placeEnemiesForRoom(String roomName, Dungeon dungeon) {
        switch (roomName) {
            case "Dungeon Entrance" -> {
                placeRandomEnemies(dungeon, 2);
            }
            case "Treasure Room" -> {
                placeRandomEnemies(dungeon, 3);
            }
        }
    }

    private void placeRandomEnemies(Dungeon dungeon, int count) {
        List<Position> positions = RandomGenerator.getRandomFloorPositions(count, dungeon);

        for (Position pos : positions) {
            Enemy enemy = createRandomEnemy(pos);
            enemies.add(enemy);
        }
    }

    private Enemy createRandomEnemy(Position position) {
        return new Goblin(position); // For now, always create a Goblin. Can be extended to create different types.
    }

    public Enemy getEnemyAt(Position position) {
        return enemies.stream()
                .filter(enemy -> enemy.getPosition().equals(position))
                .findFirst()
                .orElse(null);
    }

    public void removeEnemy(Enemy enemy) {
        enemies.remove(enemy);
    }

    public List<Enemy> getEnemies() {
        return new ArrayList<>(enemies);
    }

    public boolean hasEnemies() {
        return !enemies.isEmpty();
    }
}
