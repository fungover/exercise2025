package ExerciseTwo.Game;

import ExerciseTwo.Utils.PrintText;
import java.util.Locale;
import java.util.Scanner;

public class InputHandling {

    public String enterName(Scanner sc){

        String name;

        while(true){
            System.out.println("Welcome to the dungeons!");
            PrintText.printBold("Your quest is to find the hidden treasure");
            System.out.print("Please enter your name: ");
            name = sc.nextLine();

            if(name.isEmpty()){
                PrintText.printRed("You must enter your name");
            }else{
                return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase(Locale.ROOT);
            }
        }
    }

    public static void commands(){
        System.out.println("___________________________________________");
        System.out.println("""
                In the game you can use following commands:
                    i - inventory
                        use - use potion
                    p - check your health
                    h - help
                    quit - quit
                """);
        System.out.println("___________________________________________");
    }

}
