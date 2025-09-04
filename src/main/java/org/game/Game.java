package org.game;

import org.game.entities.Item;
import org.game.entities.Player;
import org.game.systems.InputHandler;



public class Game {
    public static void main(String[] args) {
        System.out.println("Welcome to the Dungeon Game!");

        //Todo Name and starting cords
        Player player = new Player("Hero",0,0);

        boolean running = true;

        while (running && player.isAlive()) {
            String input = InputHandler.getInput("valid commands [Move | look {not in yet} | inventory | quit]");

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
                    player.TakeDamage(1000);
                    break;

                case "additem":
                    String itemName = InputHandler.getInput("Enter item name: ");
                    String ItemType = InputHandler.getInput("Type of item: ");
                    int itemValue = Integer.parseInt(InputHandler.getInput("Enter value of item: "));
                    Item item = new Item(itemName,ItemType,itemValue);
                    player.addItem(item);
                    break;

                    case "removeitem":

                case "takedamage":
                    String takeDamage = InputHandler.getInput("Enter damage amount: ");
                    player.TakeDamage(Integer.parseInt(takeDamage));
                    System.out.println("you have "+ player.getHealth() + " hp left");
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
