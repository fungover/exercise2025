package ExerciseTwo.Service;

import ExerciseTwo.Entities.Inventory.Item;
import ExerciseTwo.Entities.Player;
import ExerciseTwo.Entities.Weapons.Weapon;

import java.util.Scanner;

public class HandleWeapon {

    public static void handleWeapon(Weapon weapon, Player player) {

        Scanner sc = new Scanner(System.in);

        weapon.getWeapons();

        while (true) {
            System.out.println("Do you want to switch weapon Y/N");
            String choice = sc.nextLine().toLowerCase();
            if(choice.equals("y")) {
                int num = weapon.numWeapons();
                System.out.println("Enter number of items to switch: ");
                int choiceOfWeapon = sc.nextInt();

                if(choiceOfWeapon < num){
                    Item getWeapon = weapon.getWeapon(choiceOfWeapon);
                    player.setWeapon(getWeapon);
                    return;
                }

            } else if (choice.equals("n")) {
                return;
            }else {
                System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
