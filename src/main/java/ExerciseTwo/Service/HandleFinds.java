package ExerciseTwo.Service;

import ExerciseTwo.Game.PlayerInput;
import ExerciseTwo.Utils.PrintText;

import java.util.Scanner;

public class HandleFinds {

    public boolean addFind(Scanner sc, PlayerInput playerInput) {
        while (true) {
            PrintText.printBlue("Do you want to add your find to your inventory Y/N?");

            String inputFromPlayer = sc.nextLine().toLowerCase();

            if(playerInput.commandInput(inputFromPlayer)){
                continue;
            }

            switch (inputFromPlayer) {
                case "y":
                    return true;
                case "n":
                    return false;
                default:
                    System.out.println("Wrong commando");
            }
        }
    }
}
