package ExerciseTwo.Game;

import ExerciseTwo.Service.PrintText;
import java.util.Locale;
import java.util.Scanner;

public class InputHandling {
    Scanner sc = new Scanner(System.in);

    public String enterName(){

        String name;

        while(true){
            System.out.println("Welcome to the dungeons!");
            PrintText.printBold("Your quest is to find the hidden treasure");
            System.out.print("Please enter your name: ");
            name = sc.nextLine();

            if(name.isEmpty()){
                PrintText.printRed("You must enter your name");
            }else{
                System.out.println("Let the adventure begin...");
                return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase(Locale.ROOT);
            }
        }
    }

    public void move(){
        System.out.println(" ");
    }

    public void commands(){
        System.out.println("""
                In the game you can use following commands:
                    l - look
                    i - inventory
                        up - use potion
                        sw - switch weapon
                    a- attack
                To move in the game use the following commands:
                    n - north
                    s - south
                    e - east
                    w - west
                """);
    }

}
