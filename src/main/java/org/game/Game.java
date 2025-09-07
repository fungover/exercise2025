package org.game;

import java.util.Collections;
import java.util.List;
import org.game.entities.Item;
import org.game.entities.Player;
import org.game.entities.npcs.Goblin;
import org.game.systems.CombatHandler;
import org.game.systems.InputHandler;
import org.game.utils.ClearConsole;
import org.game.utils.Colors;



public class Game {
    public static void main(String[] args) {
        System.out.println("Welcome to the Dungeon Game!");

        //Todo Name and starting cords
        Player player = new Player("Hero",0,0);

        boolean running = true;

        while (running && player.isAlive()) {
            String input = InputHandler.getInput(Colors.yellowColor +
                    "Valid commands: move | inventory | equip | equiptment | die | additem | takedamage | fight | spawnloot | quit"
                    + Colors.resetColor);

            switch (input) {
                case "move":
                    String dir = InputHandler.getInput("Direction (north/south/east/west)");
                    System.out.println("Direction: " + dir);
                    break;

                case "look":

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

                    case "removeitem":

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
                    player.addItem(new Item("Healing Potion","Potion",10,100));
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
