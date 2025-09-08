package game;

import entities.Item;
import entities.Player;
import map.DungeonMap;
import service.Combat;
import service.Movement;
import service.Spawner;
import utils.Position;

import java.util.Scanner;

public class Game {
    public static void main(String[] args) {
        DungeonMap map = new DungeonMap(50, 25);
        map.generate(12, 4, 8);

        Position spawn = map.findFirstWalkable();
        if (spawn == null) {
            throw new IllegalStateException("No walkable tile to spawn!");
        }
        Player player = new Player();
        player.setPosition(spawn);

        Spawner spawner = new Spawner(map);
        int enemiesPlaced = spawner.spawnRandomEnemies(6);
        int itemsPlaced = spawner.spawnRandomItems(5);

        Movement movement = new Movement(player, map);
        Combat combat = new Combat(player, map);

        System.out.printf("Spawned %d enemies and %d items.%n", enemiesPlaced, itemsPlaced);
        System.out.println("Type 'help' for commands. 'quit' to exit.");

        Scanner in = new Scanner(System.in);
        while (player.isAlive()) {
            map.printToConsole(player);
            System.out.println(player.getName() + " HP: " + player.getHealth());
            System.out.print("> ");

            String line = in.nextLine().trim().toLowerCase();
            if (line.isEmpty()) {
                continue;
            }

            if (line.equals("quit") || line.equals("exit")) {
                break;
            }
            if (line.equals("help")) {
                printHelp();
                continue;
            }

            boolean turnConsumed = false;
            switch (line) {
                case "n", "move n", "north" -> {
                    movement.move(0, -1);
                    turnConsumed = true; }
                case "s", "move s", "south" -> {
                    movement.move(0,  1);
                    turnConsumed = true; }
                case "e", "move e", "east"  -> {
                    movement.move(1,  0);
                    turnConsumed = true; }
                case "w", "move w", "west"  -> {
                    movement.move(-1, 0);
                    turnConsumed = true; }
                default -> {
                    if (line.equals("attack")) {
                        boolean fought = combat.playerAttackHere();
                        if (fought) {
                            turnConsumed = true;
                        }
                    } else if (line.equals("use potion")) {
                        boolean used = combat.useHealingPotion();
                        if (!used) {
                            System.out.println("You don't have a healing potion.");
                        }
                        else {
                            turnConsumed = true;
                        }
                    } else if (line.equals("equip")) {
                        boolean equipped = equipFirstWeapon(player);
                        if (!equipped) {
                            System.out.println("You don't have a weapon to equip.");
                        }
                        else {
                            turnConsumed = false;
                        }
                    } else if (line.equals("inventory")) {
                        player.listInventory();
                    } else {
                        System.out.println("Unknown command. Type 'help'.");
                    }
                }
            }

            if (turnConsumed && player.isAlive()) {
                combat.enemyTurn();
            }
        }

        if (!player.isAlive()) {
            System.out.println("You died. Game over.");
        }
        in.close();
    }

    private static boolean equipFirstWeapon(Player player) {
        for (Item item : player.getInventory()) {
            if ("weapon".equalsIgnoreCase(item.getType())) {
                player.useItem(item);
                return true;
            }
        }
        return false;
    }

    private static void printHelp() {
        System.out.println("""
            Commands:
              n/s/e/w or move n/s/e/w
              attack
              use potion
              equip
              inventory
              look
              help
              quit
            """);
    }
}

