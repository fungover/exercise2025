package org.example;

public class Elpris {
    public static void main(String[] args) {
        String welcomeText = """
        ====================================================
                  Welcome to ElPrice CLI!
        ====================================================
        Hello! This tool helps you make smart decisions
        about electricity usage. Find out the cheapest hours
        to charge your electric car or simply save on energy costs.
        Let's get started!
        ====================================================
        """;

        System.out.println(welcomeText);

        boolean running = true;

        do {
            System.out.println("Please choose an option:");
            System.out.println("1. Download prices for today and tomorrow");
            System.out.println("2. Calculate and display the mean price for the current 24-hour period");
            System.out.println("3. Identify the cheapest and most expensive hours");
            System.out.println("4. Suggest optimal time to charge an electric car (2, 4, or 8 hours)");
            System.out.println("5. Select a price zone (SE1/SE2/SE3/SE4)");
            System.out.println("6. Import hourly consumption data from a CSV file");
            System.out.println("0. Exit");

            /*Scanner scanner = new Scanner(System.in);*/
            String choice = System.console().readLine("Enter your choice: ");

            switch(choice) {
                case "1":
                    System.out.println("1");
                    String zone = System.console().readLine("Enter Zone Name: ");
                    System.out.println("\n\n");
                    break;
                case "2":
                    System.out.println("2");
                    System.out.println("\n\n");
                    break;
                case "3":
                    System.out.println("3");
                    System.out.println("\n\n");
                    break;
                case "4":
                    System.out.println("4");
                    System.out.println("\n\n");
                    break;
                case "5":
                    System.out.println("5");
                    System.out.println("\n\n");
                    break;
                case "6":
                    System.out.println("6");
                    System.out.println("\n\n");
                    break;
                case "0":
                    running = false;
                    System.out.println("You have exited succesfully");
                    System.out.println("\n\n");
                    break;
                default:
                    System.out.println("You have choosen an incorrect alternative, please try again.\n\n");

            }
        }
        while (running);
    }
}
