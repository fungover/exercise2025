package org.example.service;

import org.example.entities.Item;
import org.example.entities.Player;
import org.example.entities.Position;

public class GameService {
    private final RoomService roomService;
    private final MovementService movementService;
    private ItemService itemService;
    private EnemyService enemyService;
    private final DisplayService displayService;

    public GameService() {
        this.roomService = new RoomService();
        this.movementService = new MovementService();
        this.itemService = new ItemService();
        this.enemyService = new EnemyService();
        this.displayService = new DisplayService();

        itemService.placeRandomItems(roomService.getCurrentRoom().getDungeon());

        String roomName = roomService.getCurrentRoom().getName();
        enemyService.placeEnemiesForRoom(roomName, roomService.getCurrentRoom().getDungeon());
    }

    public boolean handleMovement(Player player, Direction direction) {
        boolean moved = movementService.movePlayer(player, direction, roomService.getCurrentRoom().getDungeon());

        if (moved) {
            System.out.println("Moving " + direction.toString().toLowerCase() + "...");
            checkForRoomTransition(player);
            return true;
        } else {
            System.out.println("Can't move " + direction.toString().toLowerCase() + " - obstacle in the way!");
            return false;
        }
    }

    private void checkForRoomTransition(Player player) {
        if (roomService.isPlayerOnDoor(player.getPosition())) {
            String newRoomName = roomService.switchToNextRoom();
            player.setPosition(roomService.getStartingPosition());

            itemService = new ItemService();
            itemService.placeRandomItems(roomService.getCurrentRoom().getDungeon());

            enemyService = new EnemyService();
            enemyService.placeEnemiesForRoom(newRoomName, roomService.getCurrentRoom().getDungeon());

            System.out.println("You entered: " + newRoomName);
        }
    }

    public boolean handlePickup(Player player) {
        Position playerPos = player.getPosition();
        Item item = itemService.getItemAt(playerPos);

        if (item != null) {
            player.addToInventory(item);
            itemService.removeItem(item);
            System.out.println("Picked up: " + item.getName());
            return true;
        } else {
            System.out.println("No item to pick up here.");
            return false;
        }
    }

    public void displayGameState(Player player) {
        displayService.printMapWithPlayer(roomService.getCurrentRoom(), player, itemService, enemyService);
        displayService.showGameState(player, roomService.getCurrentRoom());
    }

    public void displayInventory(Player player) {
        displayService.showInventory(player);
    }

    public RoomService getRoomService() {
        return roomService;
    }

    public ItemService getItemService() {
        return itemService;
    }

    public DisplayService getDisplayService() {
        return displayService;
    }

    public EnemyService getEnemyService() {
        return enemyService;
    }
}
