package org.example.service;

import org.example.entities.enemies.Enemy;
import org.example.entities.Item;
import org.example.entities.Player;
import org.example.entities.Position;
import org.example.utils.RandomGenerator;

import java.util.Scanner;

public class GameService {
    private final RoomService roomService;
    private final MovementService movementService;
    private ItemService itemService;
    private EnemyService enemyService;
    private final DisplayService displayService;
    private final LootService lootService;
    private CombatService combatService;
    private final RandomGenerator randomGenerator;

    public GameService() {
        this.randomGenerator = new RandomGenerator();
        this.roomService = new RoomService();
        this.movementService = new MovementService();
        this.itemService = new ItemService(randomGenerator);
        this.enemyService = new EnemyService(randomGenerator);
        this.displayService = new DisplayService();
        this.lootService = new LootService(randomGenerator);
        this.combatService = new CombatService(displayService, itemService);


        itemService.placeRandomItems(roomService.getCurrentRoom().getDungeon());

        String roomName = roomService.getCurrentRoom().getName();
        enemyService.placeEnemiesForRoom(roomName, roomService.getCurrentRoom().getDungeon());
    }

    //Constructor used for testing.
    public GameService(RandomGenerator randomGenerator) {
        this.randomGenerator = randomGenerator;
        this.roomService = new RoomService();
        this.movementService = new MovementService();
        this.itemService = new ItemService(randomGenerator);
        this.enemyService = new EnemyService(randomGenerator);
        this.displayService = new DisplayService();
        this.lootService = new LootService(randomGenerator);
        this.combatService = new CombatService(displayService, itemService);
    }

    public boolean handleMovement(Player player, Direction direction) {
        boolean moved = movementService.movePlayer(player, direction, roomService.getCurrentRoom().getDungeon());

        if (moved) {
            System.out.println(">Moving " + direction.toString().toLowerCase() + "...");
            checkForRoomTransition(player);
            return true;
        } else {
            System.out.println(">Can't move " + direction.toString().toLowerCase() + " - obstacle in the way!");
            return false;
        }
    }

    private void checkForRoomTransition(Player player) {
        if (roomService.isPlayerOnDoor(player.getPosition())) {
            String newRoomName = roomService.switchToNextRoom();
            player.setPosition(roomService.getStartingPosition());

            itemService = new ItemService(randomGenerator);
            itemService.placeRandomItems(roomService.getCurrentRoom().getDungeon());

            enemyService = new EnemyService(randomGenerator);
            enemyService.placeEnemiesForRoom(newRoomName, roomService.getCurrentRoom().getDungeon());

            combatService = new CombatService(displayService, itemService);

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

    public void handleEquip(Player player, Scanner scanner) {
        itemService.equipItemFromInventory(player, scanner, displayService);
    }

    public boolean handleFight(Player player, Scanner scanner) {
        Position playerPos = player.getPosition();
        Enemy enemy = enemyService.getEnemyAt(playerPos);

        if (enemy != null) {
            boolean playerSurvived = combatService.startCombat(player, enemy, scanner);

            if (!playerSurvived) {
                return false; // Player died
            }

            if (enemy.isDead()) {
                enemyService.removeEnemy(enemy);
            }
            return true;
        } else {
            System.out.println("No enemy to fight here.");
            return true;
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

    public MovementService getMovementService() {
        return movementService;
    }

    public LootService getLootService() {
        return lootService;
    }
}
