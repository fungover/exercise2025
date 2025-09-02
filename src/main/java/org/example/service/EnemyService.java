package org.example.service;

import org.example.entities.Position;
import org.example.entities.enemies.*;
import org.example.map.Dungeon;
import org.example.utils.RandomGenerator;

import java.util.ArrayList;
import java.util.List;

public class EnemyService {
    private final List<Enemy> enemies;
    private final RandomGenerator randomGenerator;

    public EnemyService() {
        this.enemies = new ArrayList<>();
        this.randomGenerator = new RandomGenerator();
    }

    //Constructor for testing.
    public EnemyService(RandomGenerator randomGenerator) {
        this.enemies = new ArrayList<>();
        this.randomGenerator = randomGenerator;
    }

    public void placeEnemiesForRoom(String roomName, Dungeon dungeon) {
        switch (roomName) {
            case "Dungeon Entrance" -> placeRandomEnemies(dungeon, 4);
            case "Treasure Room" -> placeRandomEnemies(dungeon, 3);
            case "Dragon's Lair" -> placeBoss(dungeon);
        }
    }

    private void placeRandomEnemies(Dungeon dungeon, int count) {
        List<Position> positions = randomGenerator.getRandomFloorPositions(count, dungeon);

        for (Position pos : positions) {
            Enemy enemy = createRandomEnemy(pos);
            enemies.add(enemy);
        }
    }

    private void placeBoss(Dungeon dungeon) {
        Position bossPos = randomGenerator.findRandomFloorPosition(dungeon);
        if (bossPos != null) {
            Dragon dragon = new Dragon(bossPos);
            enemies.add(dragon);
        }
    }

    private Enemy createRandomEnemy(Position position) {
        return randomGenerator.nextBoolean() ? new Goblin(position) : new Orc(position);
    }

    public Enemy getEnemyAt(Position position) {
        return enemies.stream()
                .filter(enemy -> enemy.getPosition().equals(position))
                .findFirst()
                .orElse(null);
    }

    public Enemy getBossAt(Position position) {
        return enemies.stream()
                .filter(enemy -> enemy instanceof Boss && enemy.getPosition().equals(position))
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
