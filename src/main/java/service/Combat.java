package service;

import entities.Enemy;
import entities.HealingPotion;
import entities.Item;
import entities.Player;
import map.DungeonMap;
import map.Tile;
import utils.Position;

public class Combat {
    private final Player player;
    private final DungeonMap dungeonMap;

    public Combat(Player player, DungeonMap dungeonMap) {
        this.player = player;
        this.dungeonMap = dungeonMap;
    }

    public boolean playerAttackHere() {
        Position playerPosition = player.getPosition();
        Tile tile = dungeonMap.getTile(playerPosition.getX(), playerPosition.getY());
        Enemy enemy = tile.getEnemy();

        if (enemy == null) {
            return false;
        }

        player.attack(enemy);

        if (!enemy.isAlive()) {
            tile.setEnemy(null);
        } else {
            System.out.println(enemy.getType() + " has " + enemy.getHealth() + " hp");
        }
        return true;
    }

    public void enemyTurn() {
        Position playerPosition = player.getPosition();
        Tile tile = dungeonMap.getTile(playerPosition.getX(), playerPosition.getY());
        Enemy enemy = tile.getEnemy();

        if (enemy != null && enemy.isAlive()) {
            enemy.attack(player);
        }
    }

    public boolean useHealingPotion() {
        for (Item item : player.getInventory()) {
            if (item instanceof HealingPotion healingPotion) {
                player.useItem(healingPotion);
                return true;
            }
        }
        return false;
    }
}
