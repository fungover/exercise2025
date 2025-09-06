package ExerciseTwo.Service;

import ExerciseTwo.Entities.Inventory.Item;
import ExerciseTwo.Entities.Player;
import ExerciseTwo.Entities.Weapons.Weapon;
import ExerciseTwo.Utils.PrintText;

import java.util.Scanner;

public class HandleWeapon {

    public static void handleWeapon(Weapon weapon, Player player) {

        Scanner sc = new Scanner(System.in);

        boolean checkWeapon = weapon.getWeapons();

        if (checkWeapon){
            while (true) {
                PrintText.printBlue("Do you want to switch weapon Y/N");
                String choice = sc.nextLine().toLowerCase();
                if (choice.equals("y")) {
                    int num = weapon.numWeapons();
                    PrintText.printBlue("Enter number for the item you want to switch to: ");
                    String choiceOfWeapon = sc.nextLine();
                    int numOfWeapon = Integer.parseInt(choiceOfWeapon);

                    if (numOfWeapon < num) {
                        Item getWeapon = weapon.getWeapon(numOfWeapon);
                        player.setWeapon(getWeapon);
                        return;
                    }

                } else if (choice.equals("n")) {
                    return;
                } else {
                    System.out.println("Invalid choice. Try again.");
                }
            }
        }
    }
}
