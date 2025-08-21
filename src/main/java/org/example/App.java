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
        String area = getArea(scanner);
        int choice = getChoice(scanner);

        handleChoice(choice, area);

        /* String URL = "https://www.elprisetjustnu.se/api/v1/prices/2025/08-19_SE3.json";
        getPrices(URL); */
    }

    private static String getArea(Scanner scanner) {
        int area;

        do {
            System.out.println("1. Luleå / Northern Sweden");
            System.out.println("2. Sundsvall / Northern Central Sweden");
            System.out.println("3. Stockholm / Southern Central Sweden");
            System.out.println("4. Malmö / Southern Sweden");
            System.out.print("Enter your choice: ");

            if (scanner.hasNextInt()) {
                area = scanner.nextInt();
                if (area < 1 || area > 4) {
                    System.out.println("Invalid choice, must be between 1 and 4");
                }
            } else {
                System.out.println("Invalid choice, must be a number between 1 and 4");
                scanner.next();
                area = -1;
            }
        } while (area < 1 || area > 4);

        return "SE" + area;
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
                if (choice < 1 || choice > 5) {
                    System.out.println("Invalid choice, must be between 1 and 5");
                }
            } else {
                System.out.println("Invalid choice, must be a number between 1 and 5");
                scanner.next();
                choice = -1;
            }
        } while (choice < 1 || choice > 5);

        return choice;
    }

    private static String handleChoice(int choice, String area) {

        switch (choice) {
            case 1:
                System.out.println("Downloading prices for the current day and next day");
                String result = urlBuilder( "123", area);
                System.out.println(result);
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

    private static String urlBuilder(String date, String zone) {
        String year = String.valueOf(java.time.LocalDate.now().getYear());
        return "https://www.elprisetjustnu.se/api/v1/prices/" + year + "/" + date + "_" + zone + ".json";
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
