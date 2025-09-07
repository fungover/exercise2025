package service;

import entities.Enemy;
import entities.Item;
import entities.Player;
import map.DungeonMap;
import map.Tile;
import utils.Position;

public class Movement {
    private final Player player;
    private final DungeonMap dungeon;

    public Movement(Player player, DungeonMap dungeon) {
        this.player = player;
        this.dungeon = dungeon;
    }

    public void move(int dx, int dy) {
        Position playerPosition = player.getPosition();
        int newX = playerPosition.getX() + dx;
        int newY = playerPosition.getY() + dy;
        Tile destinationTile = dungeon.getTile(newX, newY);

        if (!dungeon.inBounds(newX, newY)) {
            System.out.println("You can't go out of the map");
            return;
        }

        if (!destinationTile.isWalkable()) {
            System.out.println("A wall is blocking you from going there");
            return;
        }

        player.move(newX, newY);
        System.out.println("Moved to" + player.getPosition());

        if (destinationTile.getItem() != null) {
            Item item = destinationTile.getItem();
            player.addItem(item);
            destinationTile.setItem(null);
        }

        if (destinationTile.getEnemy() != null) {
            Enemy enemy = destinationTile.getEnemy();
            System.out.println("There is a " + enemy.getType());
        }

    }
}
