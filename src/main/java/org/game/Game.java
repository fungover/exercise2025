package org.game;

import org.game.entities.Item;
import org.game.entities.Player;
import org.game.entities.npcs.Goblin;
import org.game.systems.CombatHandler;
import org.game.systems.InputHandler;
import org.game.utils.ClearConsole;


public class Game {
    public static void main(String[] args) {
        System.out.println("Welcome to the Dungeon Game!");

        //Todo Name and starting cords
        Player player = new Player("Hero",0,0);

        boolean running = true;

        while (running && player.isAlive()) {
            String input = InputHandler.getInput("Valid commands: move| look {not in yet} | inventory | quit]");

            switch (input) {
                case "move":
                    String dir = InputHandler.getInput("Direction (north/south/east/west)");
                    System.out.println("Direction: " + dir);
                    break;

                case "look":

                case "inventory":
                    player.showInventory();
                    break;

                case "quit":
                    running = false;
                    break;

                case "skills":

                    // Todo dev commands
                case "die":
                    player.takeDamage(1000,true);
                    break;

                case "additem":
                    String itemName = InputHandler.getInput("Enter item name: ");
                    String ItemType = InputHandler.getInput("Type of item: ");
                    int itemValue = Integer.parseInt(InputHandler.getInput("Enter value of item: "));
                    int itemQuantity = Integer.parseInt(InputHandler.getInput("Enter value of item quantity: "));
                    Item item = new Item(itemName,ItemType,itemValue, itemQuantity);
                    player.addItem(item);
                    break;

                    case "removeitem":

                case "takedamage":
                    String takeDamage = InputHandler.getInput("Enter damage amount: ");
                    player.takeDamage(Integer.parseInt(takeDamage),false);
                    System.out.println("you have "+ player.getHealth() + " hp left");
                    break;

                case "spawnenemy":
                    for (int i =0; i < 1000; i++) {
                        //System.out.println("Spawning Goblin");
                        Goblin testGoblin = new Goblin(0, 0);
                        testGoblin.setHealth(0);
                        //System.out.println("you have defeated the Goblin");

                        Item loots = testGoblin.getLoot();
                        if (loots != null) {
                           // System.out.println("The Goblin dropped " + loots.getName()+ " x " + loots.getQuantity());
                            player.addItem(loots);
                        } else {
                          //  System.out.println("The Goblin dropped nothing");
                        }
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
