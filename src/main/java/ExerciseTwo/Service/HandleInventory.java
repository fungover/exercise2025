package ExerciseTwo.Service;

import ExerciseTwo.Entities.Inventory.Inventory;
import ExerciseTwo.Entities.Inventory.Item;
import ExerciseTwo.Entities.Inventory.Potion;
import ExerciseTwo.Entities.Player;

import java.util.Scanner;

public class HandleInventory {

    public static void handleInventory(Inventory inventory, Player player) {

        Scanner sc = new Scanner(System.in);
        Item item = new Potion();

        boolean potionToUSe = inventory.getInventory();
        if(potionToUSe) {

            while(true){
                System.out.println("Do you want to use a potion Y/N:");
                String choice = sc.nextLine().toLowerCase();

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
