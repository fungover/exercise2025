package org.example.utils;

import org.example.entities.Enemy;

import java.util.ArrayList;
import java.util.List;

public class Enemies {
    private final List<Enemy> enemies = new ArrayList<>();

    public void addToList(Enemy enemy) {
        enemies.add(enemy);
    }

    public Enemy getEnemyOnFloor(int x, int y) {
        for (Enemy enemy : enemies) {
            if (enemy.getX() == x && enemy.getY() == y) {
                return enemy;
            }
        }
        System.out.println("No enemy found.");
        return null;
    }
}
