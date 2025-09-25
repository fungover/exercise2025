package ExerciseTwo.Service;

import ExerciseTwo.Entities.Inventory.Inventory;
import ExerciseTwo.Entities.Inventory.Item;
import ExerciseTwo.Entities.Inventory.Potion;
import ExerciseTwo.Entities.Player;
import ExerciseTwo.Utils.PrintText;

import java.util.Scanner;

public class HandleInventory {

    public static void handleInventory(Inventory inventory, Player player, Scanner sc) {

        Item item = new Potion();

        boolean potionToUSe = inventory.getInventory();
        if(potionToUSe) {

            while(true){
                PrintText.printBlue("Do you want to use a potion Y/N:");
                String choice = sc.nextLine().trim().toLowerCase();

                if (choice.equals("y")) {
                    int potion = item.getEffect();
                    player.setHealth(potion);
                    player.playerHealth();
                    inventory.removeItem("Potion");
                    return;
                }
                else if (choice.equals("n")) {
                    return;
                }else{
                    System.out.println("Invalid choice. Try again.");
                }

            }
        }
    }
}
