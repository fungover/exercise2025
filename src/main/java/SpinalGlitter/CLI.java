package SpinalGlitter;

import java.util.Scanner;

public class CLI {
    public static void main(String[] args) {
        System.out.println("Hello There!");
        // You can add more functionality here as needed

        Scanner input = new Scanner(System.in);
        boolean CliRunning = true;


        while (CliRunning) {

            // Display the options for the user.
            System.out.println("Hello! Welcome to SpinalGlitter CLI!");
            System.out.println("Please choose an option:");
            System.out.println("1 Show the electricity price for today");
            System.out.println("2 Show the average electricity price for today");
            System.out.println("3 Show the electricity price for tomorrow");
            System.out.println("4 Show the average electricity price for tomorrow");
            System.out.println("5 Show the cheapest and most expensive hour for today");
            System.out.println("6 Show the the best ours to charge your car for today");
            System.out.println("7 Exit");

            // Read the user input
            String userInput = input.nextLine().trim();

            // Process the user input
            switch (userInput) {
                case "1":
                    System.out.println("You chose option 1: Show the electricity price for today.");


        }
    }
}
