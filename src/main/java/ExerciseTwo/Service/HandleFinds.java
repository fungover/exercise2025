package ExerciseTwo.Service;

import ExerciseTwo.Entities.Item;

import java.util.Scanner;

public class HandleFinds {

    public boolean addFind(Scanner sc){
        while (true) {
            System.out.println("Do you want to add your find to your inventory y/n?");
            String answer = sc.next().toLowerCase();
            switch (answer) {
                case "y" -> {
                    return true;
                }
                case "n" ->{
                    return false;
                }
                default -> {
                    System.out.println("Wrong commando");
                }
            }
        }
    }



}
