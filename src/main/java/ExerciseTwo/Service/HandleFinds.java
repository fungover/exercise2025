package ExerciseTwo.Service;

import ExerciseTwo.Game.PlayerInput;

import java.util.Scanner;

public class HandleFinds {

    public boolean addFind(Scanner sc){
        while (true) {
            System.out.println("Do you want to add your find to your inventory y/n?");

            String inputFromPlayer = sc.nextLine().toLowerCase();
            PlayerInput input = new PlayerInput();
            if(input.commandInput(inputFromPlayer)){
                continue;
            }

            switch (inputFromPlayer) {
                case "y" :return true;
                case "n" : return false;
                default: System.out.println("Wrong commando");
            }
        }
    }



}
