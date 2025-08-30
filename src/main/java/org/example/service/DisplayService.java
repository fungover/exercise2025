package org.example.service;

import org.example.entities.Item;
import org.example.entities.Player;
import org.example.entities.Position;
import org.example.map.Room;
import org.example.map.TileType;

import java.util.List;

public class DisplayService {

    public void printMapWithPlayer(Room room, Player player, ItemService itemService, EnemyService enemyService) {
        Position playerPos = player.getPosition();

        for (int y = 0; y < room.getDungeon().getRows(); y++) {
            for (int x = 0; x < room.getDungeon().getColumns(); x++) {
                Position currentPos = new Position(x, y);

                if (x == playerPos.getX() && y == playerPos.getY()) {
                    System.out.print(TileType.PLAYER.getSymbol());
                } else if (enemyService.getEnemyAt(currentPos) != null) {
                    System.out.print(TileType.ENEMY.getSymbol());

                } else if (itemService.getItemAt(currentPos) != null) {
                    System.out.print(TileType.ITEM.getSymbol());
                } else {
                    System.out.print(room.getDungeon().getTile(x, y).getSymbol());
                }
            }
            System.out.println();
        }
    }

    public void showGameState(Player player, Room room) {
        System.out.println("Current Room: " + room.getName());
        System.out.println(player);
        System.out.println("Movement: North (N), South (S), East (E), West (W), Quit (Q)");
        System.out.println("Actions: Pickup (P), Inventory (I), Use (U)");
    }

    public void showInventory(Player player) {
        List<Item> playerInventory = player.getInventory();
        if (playerInventory.isEmpty()) {
            System.out.println("Your inventory is empty.");
        } else {
            System.out.println("Inventory: ");
            for (int i = 0; i < playerInventory.size(); i++) {
                System.out.println((i + 1) + ". " + playerInventory.get(i).getName());
            }
        }
    }
}
