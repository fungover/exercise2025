package org.game;

import java.util.Collections;
import java.util.List;
import org.game.entities.Enemy;
import org.game.entities.Item;
import org.game.entities.Player;
import org.game.entities.npcs.Goblin;
import org.game.systems.CombatHandler;
import org.game.systems.InputHandler;
import org.game.utils.ClearConsole;
import org.game.utils.Colors;
import org.game.world.Dungeon;
import org.game.world.Room;
import org.game.world.Tile;


public class Game {
    public static void main(String[] args) {
        System.out.println("Welcome to the Dungeon Game!");

        //Todo Name and starting cords
        Player player = new Player("Hero",1,1);
        Dungeon dungeon = new Dungeon(10,10);
        boolean running = true;

        while (running && player.isAlive()) {
           // dungeon.printDungeon(player.getX(), player.getY());

            System.out.println(Colors.blueColor + "Dev commands: die | additems | takedamage | spawnloot | loot | fight " + Colors.resetColor);
            String input = InputHandler.getInput("%sValid commands: move | inventory | equip | equiptment | quit%s".formatted(Colors.yellowColor, Colors.resetColor));


            switch (input) {
                case "move":
                    String dir = InputHandler.getInput("Direction (north/south/east/west)");
                    int newX = player.getX();
                    int newY = player.getY();

                    switch (dir.toLowerCase()){
                        case "north": newY--; break;
                        case "south": newY++; break;
                        case "east": newX++; break;
                        case "west": newX--; break;
                        default:
                            System.out.println("Invalid direction");
                            break;
                    }

                    if (dungeon.isWalkable(newX, newY)) {
                        player.setPosition(newX,newY);

                        Tile tile = dungeon.getTile(player.getX(), player.getY());
                        if (tile != null && tile.getRoom() != null && !tile.getRoom().IsVisited()) {
                            Room room = tile.getRoom();
                            System.out.println("You encountered a " + room.getType() + "room!");

                            for (Enemy e: room.getEnemies()) {
                                CombatHandler.fight(player,e);
                            }

                            for (Item loot : room.getLoot()) {
                                player.addItem(loot);
                            }

                            room.setVisited(true);

                        }

                        System.out.println("You moved " + dir);
                        System.out.println("Press enter to continue...");
                        new java.util.Scanner(System.in).nextLine();
                        ClearConsole.clearConsole();
                    } else {
                        System.out.println("You run into a wall! You should try another way...");
                    }
                    break;

                case "look":
                    System.out.println("you look around...");
                    dungeon.printDungeon(player.getX(), player.getY());
                    break;

                case "inventory":
                    player.showInventory();
                    break;

                case "equip":
                    System.out.println("What do you want to equip?");
                    player.showInventory();
                    String equipItem = InputHandler.getInput("Enter item name: ");
                    player.equipItem(equipItem);
                    break;

                case "armour":
                    player.showEquipment();
                    break;

                case "quit":
                    running = false;
                    break;

                    // Todo dev commands
                case "die":
                    player.takeDamage(1000,true);
                    break;

                case "additem":
                    String itemName = InputHandler.getInput("Enter item name: ");
                    String ItemType = InputHandler.getInput("Type of item: ");
                    int itemValue = Integer.parseInt(InputHandler.getInput("Enter value of item: "));
                    int itemQuantity = Integer.parseInt(InputHandler.getInput("Enter value of item quantity: "));
                    System.out.println("What slot type?: HEAD|CHEST|OFFHAND|WEAPON|LEGS|GLOVES|BOOTS");
                    List<String> itemSlot = Collections.singletonList(InputHandler.getInput("Enter slot type:"));
                    Item item = new Item(itemName,ItemType,itemValue, itemQuantity,itemSlot);
                    player.addItem(item);
                    break;

                    case "colors":

                        System.out.println(Colors.blackColor+"test"+Colors.resetColor);
                        System.out.println(Colors.redColor+"test"+Colors.resetColor);
                        System.out.println(Colors.greenColor+"test"+Colors.resetColor);
                        System.out.println(Colors.yellowColor+"test"+Colors.resetColor);
                        System.out.println(Colors.blueColor+"test"+Colors.resetColor);
                        System.out.println(Colors.magentaColor+"test"+Colors.resetColor);
                        System.out.println(Colors.cyanColor+"test"+Colors.resetColor);
                        System.out.println(Colors.whiteColor+"test"+Colors.resetColor);

                        System.out.println(Colors.blackBackground+"test"+Colors.resetColor);
                        System.out.println(Colors.redBackground+"test"+Colors.resetColor);
                        System.out.println(Colors.greenBackground+"test"+Colors.resetColor);
                        System.out.println(Colors.yellowBackground+"test"+Colors.resetColor);
                        System.out.println(Colors.blueBackground+"test"+Colors.resetColor);
                        System.out.println(Colors.magentaBackground+"test"+Colors.resetColor);
                        System.out.println(Colors.cyanBackground+"test"+Colors.resetColor);
                        System.out.println(Colors.whiteBackground+"test"+Colors.resetColor);
                        System.out.println(""+Colors.resetColor);
                        break;

                case "takedamage":
                    String takeDamage = InputHandler.getInput("Enter damage amount: ");
                    player.takeDamage(Integer.parseInt(takeDamage),false);
                    System.out.println("you have "+ player.getHealth() + " hp left");
                    break;

                case "spawnloot":
                    for (int i =0; i < 5; i++) {
                        Goblin testGoblin = new Goblin(0, 0);
                        testGoblin.setHealth(0);

                        Item loots = testGoblin.getLoot();

                            player.addItem(loots);

                    }
                    break;

                case "fight":
                    ClearConsole.clearConsole();
                    Goblin goblin = new Goblin(0,0);
                    System.out.println("Fighting a " + goblin.getName());
                    CombatHandler.fight(player, goblin);
                    break;

                case "loot":
                    player.addItem(new Item("Healing Potion","consumable",10,100,true,10));
                    player.addItem(new Item("Bread","consumable",10,100,true,10));
                    break;

                default:
                    System.out.println("Unknown command.");
            }
        }
        if ((player.isAlive() && !running)) {
            System.out.println("Thanks for playing!");
        }else if (!player.isAlive()) {
            System.out.println("Oh dear you died!");
        }else
        System.out.println("Game over.");
    }

}
