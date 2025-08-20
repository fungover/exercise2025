package org.example;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        System.out.println("Choose option:");

        Scanner scanner = new Scanner(System.in);
        int choice = getChoice(scanner);

        handleChoice(choice);

        /* String URL = "https://www.elprisetjustnu.se/api/v1/prices/2025/08-19_SE3.json";
        getPrices(URL); */
    }

    private static int getChoice(Scanner scanner) {
        int choice;

        do {
            System.out.println("1. Download prices for the current day and next day");
            System.out.println("2. Print the mean price for the current 24-hour period");
            System.out.println("3. Identify and print the hours with the cheapest and most expensive prices");
            System.out.println("4. Determine the best time to charge an electric car for durations of 2, 4, or 8 hours");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                if (choice < 1 || choice > 4) {
                    System.out.println("Invalid choice, must be between 1 and 4");
                }
            } else {
                System.out.println("Invalid choice, must be a number between 1 and 4");
                scanner.next();
                choice = -1;
            }
        } while (choice < 1 || choice > 4);

        return choice;
    }

    private static String handleChoice(int choice) {

        switch (choice) {
            case 1:
                System.out.println("Downloading prices for the current day and next day");
                break;
            case 2:
                System.out.println("Printing the mean price for the current 24-hour period");
                break;
            case 3:
                System.out.println("Identifying and printing the hours with the cheapest and most expensive prices");
                break;
            case 4:
                System.out.println("Determining the best time to charge an electric car for durations of 2, 4, or 8 hours");
                break;
            case 5:
                System.out.println("Exiting");
                break;
        }

        return null;
    }

    static void getPrices(String url_string) {
        try {
            URL url = new URL(url_string);
            URLConnection connection = url.openConnection();
            InputStream inputStream = connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String response;
            while ((response = bufferedReader.readLine()) != null) {
                System.out.println(response);
                bufferedReader.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
