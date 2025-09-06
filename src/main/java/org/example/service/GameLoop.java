package org.example.service;

import org.example.entities.*;
import org.example.map.Dungeon;
import org.example.map.Tile;

import java.util.Scanner;

public class GameLoop {

    private final Dungeon dungeon;
    private final Player hero;
    private final MovementService movementService;
    private final CombatService combatService;
    private final Scanner scanner;


    private boolean victory = false;

    public GameLoop(Dungeon dungeon, Player hero) {
        this.dungeon = dungeon;
        this.hero = hero;
        this.movementService = new MovementService(dungeon);
        this.combatService = new CombatService();
        this.scanner = new Scanner(System.in);
    }

    public boolean hasVictory() {
        return victory;
    }

    public void run() {
        boolean running = true;

        while (running && hero.isAlive()) {
            dungeon.printDungeon(hero.getPosition());

            System.out.print("Inventory: ");
            if (hero.getInventory().isEmpty()) System.out.println("Empty");
            else hero.getInventory().showItems();

            System.out.println("Move (w/a/s/d), use item (u), or quit (q):");
            String input = scanner.nextLine().trim().toLowerCase();
            running = handleInput(input);
        }

        System.out.println("Game over!");
    }

    private boolean handleInput(String input) {
        if (input.equals("u")) {
            useItem();
            return true;
        }

        if (input.equals("q")) return false;

        movePlayer(input);
        return  !victory;
    }

    private void useItem() {
        if (hero.getInventory().isEmpty()) {
            System.out.println("No items to use!");
            return;
        }

        hero.getInventory().showItems();
        System.out.println("Enter item number to use:");
        try {
            int index = Integer.parseInt(scanner.nextLine());
            hero.getInventory().useItem(index, hero);
        } catch (Exception e) {
            System.out.println("Invalid choice!");
        }
    }

    public void movePlayer(String input) {
        Position newPos = switch (input) {
            case "w" -> hero.getPosition().translate(0, -1);
            case "s" -> hero.getPosition().translate(0, 1);
            case "a" -> hero.getPosition().translate(-1, 0);
            case "d" -> hero.getPosition().translate(1, 0);
            default -> null;
        };

        if (newPos == null) {
            System.out.println("Invalid command!");
            return;
        }

        Tile targetTile = dungeon.getTile(newPos);
        if (targetTile != null && targetTile.isWalkable()) {
            movementService.move(hero, newPos);
            handleTile(targetTile);
        } else {
            System.out.println("You can't move there!");
        }
        System.out.println(hero.getName() + " has " + hero.getHp() + "/" + hero.getMaxHP() + " HP" +
                " | Damage: " + hero.getDamage());
    }

    private void handleTile(Tile targetTile) {
        if (targetTile.getEnemy() != null) {
            Enemy enemy = targetTile.getEnemy();
            combatService.fight(hero, enemy);

            if (!enemy.isAlive()) {
                targetTile.setEnemy(null);

                if (enemy.getName().equals("Dragon")) {
                    victory = true;
                    System.out.println("You defeated the Dragon! You won the game!");
                    return;
                }
            }
        }

        if (targetTile.getItem() != null && targetTile.getEnemy() == null) {
            Item item = targetTile.getItem();
            hero.getInventory().addItem(item);
            targetTile.setItem(null);
            System.out.println(item.getName() + " added to inventory!");
        }
    }
}