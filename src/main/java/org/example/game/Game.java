package org.example.game;

import org.example.entities.*;
import org.example.entities.Character;
import org.example.map.Dungeon;
import org.example.map.Tile;
import org.example.map.TileType;
import org.example.service.CombatService;
import org.example.service.MovementService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Game {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        int width = 12;
        int height = 12;
        Dungeon dungeon = new Dungeon(width, height);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (random.nextDouble() < 0.2) {
                    dungeon.getTile(new Position(x, y)).setType(TileType.WALL);
                }
            }
        }

        Player hero = new Player("Hero", 30, 5, randomFloorPosition(dungeon, random));

        MovementService movementService = new MovementService(dungeon);
        CombatService combatService = new CombatService();

        List<Enemy> enemies = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Position pos = randomFloorPosition(dungeon, random);
            Enemy troll = new Enemy("Troll", 10 + random.nextInt(10), 3 + random.nextInt(3), pos) {
                @Override
                public String getType() { return "Troll"; }
            };
            dungeon.getTile(pos).setEnemy(troll);
            enemies.add(troll);
        }

        for (int i = 0; i < 3; i++) {
            Position pos = randomFloorPosition(dungeon, random);
            Enemy witch = new Enemy("Witch", 8 + random.nextInt(8), 4 + random.nextInt(2), pos) {
                @Override
                public String getType() { return "Witch"; }
            };
            dungeon.getTile(pos).setEnemy(witch);
            enemies.add(witch);
        }

        for (int i = 0; i < 6; i++) {
            Position pos = randomFloorPosition(dungeon, random);
            dungeon.getTile(pos).setItem(Item.getRandomItem());
        }

        Position treasurePos = randomFloorPosition(dungeon, random);
        Enemy dragon = new Enemy("Dragon", 40, 8, treasurePos) {
            @Override
            public String getType() { return "Dragon"; }
        };
        dungeon.getTile(treasurePos).setEnemy(dragon);

        Item legendaryTreasure = new Item("Legendary Treasure") {
            @Override
            public void use(Character character) {
                System.out.println(character.getName() + " admires the Legendary Treasure. Victory!");
            }
        };
        dungeon.getTile(treasurePos).setItem(legendaryTreasure);


        boolean running = true;

        while (running && hero.isAlive()) {
            dungeon.printDungeon(hero.getPosition());
            System.out.print("Inventory: ");
            if (hero.getInventory().isEmpty()) System.out.println("Empty");
            else hero.getInventory().showItems();

            System.out.println("Move (w/a/s/d), use item (u), or quit (q):");
            String input = scanner.nextLine();

            if (input.equals("u")) {
                if (hero.getInventory().isEmpty()) {
                    System.out.println("No items to use!");
                } else {
                    hero.getInventory().showItems();
                    System.out.println("Enter item number to use:");
                    try {
                        int index = Integer.parseInt(scanner.nextLine());
                        hero.getInventory().useItem(index, hero);
                    } catch (Exception e) {
                        System.out.println("Invalid choice!");
                    }
                }
                continue;
            }

            if (input.equals("q")) {
                running = false;
                break;
            }

            Position newPos = switch (input) {
                case "w" -> hero.getPosition().translate(0, -1);
                case "s" -> hero.getPosition().translate(0, 1);
                case "a" -> hero.getPosition().translate(-1, 0);
                case "d" -> hero.getPosition().translate(1, 0);
                default -> hero.getPosition();
            };

            Tile targetTile = dungeon.getTile(newPos);
            if (targetTile != null && targetTile.isWalkable()) {
                movementService.move(hero, newPos);

                if (targetTile.getEnemy() != null) {
                    Enemy enemy = targetTile.getEnemy();
                    combatService.fight(hero, enemy);

                    if (!enemy.isAlive()) {
                        targetTile.setEnemy(null);

                        if (enemy.getName().equals("Dragon") && hero.isAlive()) {
                            Item treasure = targetTile.getItem();
                            if (treasure != null) {
                                hero.getInventory().addItem(treasure);
                                targetTile.setItem(null);
                                System.out.println("You defeated the Dragon and claimed the " + treasure.getName() + "!");
                                System.out.println("You won the game!");
                                running = false;
                                break;
                            }
                        }
                    }
                }

                if (running && targetTile.getItem() != null) {
                    Item item = targetTile.getItem();
                    hero.getInventory().addItem(item);
                    System.out.println(item.getName() + " added to inventory!");
                    targetTile.setItem(null);
                }
            } else {
                System.out.println("You can't move there!");
            }
        }

        System.out.println("Game over!");
    }

    private static Position randomFloorPosition(Dungeon dungeon, Random random) {
        Position pos;
        do {
            int x = random.nextInt(dungeon.getWidth());
            int y = random.nextInt(dungeon.getHeight());
            pos = new Position(x, y);
        } while (!dungeon.getTile(pos).isWalkable() || dungeon.getTile(pos).getEnemy() != null || dungeon.getTile(pos).getItem() != null);
        return pos;
    }
}
