package org.game.systems;

import org.game.entities.Enemy;
import org.game.entities.Item;
import org.game.entities.Player;
import org.game.utils.ClearConsole;

import java.util.List;

public class CombatHandler {
    public static void fight(Player player, Enemy enemy) {
        System.out.println("A Wild " + enemy.getName() + " appears!");

        while (player.isAlive() && enemy.isAlive() ) {
            String combatTurn = InputHandler.getInput("Its your turn: Attack | Use Item | Run ");

            switch (combatTurn) {
                case "attack":
                    player.attackDamage(enemy,true);
                    if (enemy.isAlive()) {
                        enemy.attackDamage(player,false);
                    }
                    System.out.println("Press enter to continue...");
                    new java.util.Scanner(System.in).nextLine();
                    ClearConsole.clearConsole();

                    break;

                case "use":
                    player.showInventory();
                    String itemName = InputHandler.getInput("Which item would you like to use? ");
                    player.useItem(itemName);
                    break;

                case "run":
                    System.out.println("You run away from the battle!");
                    return;
                default:
                    System.out.println("Invalid action!");
            }


            if (!enemy.isAlive()) {
                System.out.println("You defeated the " + enemy.getName() + " !");

                List<Item> lootlist = enemy.dropLoot();

                if (lootlist.isEmpty()) {
                    System.out.println("There is no loot!");
                } else {
                    System.out.println("You found ");
                    for (Item loot: lootlist) {
                        player.addItem(loot);
                    }
                }

            }

            if (!player.isAlive()){
            System.out.println("You were defeated by a " + enemy.getName() + "...");
            }

        }


    }
}
