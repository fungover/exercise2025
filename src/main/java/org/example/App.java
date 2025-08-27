package org.example;

import org.example.api.ApiClient;
import org.example.cli.Menu;
import org.example.cli.ZoneSelection;

import java.util.Scanner;

public class App {
    public static void main(String[] args) throws java.io.IOException, InterruptedException {

        try (Scanner mainScanner = new Scanner(System.in)) { // Using try-with-resources to ensure the scanner is closed automatically.

            ZoneSelection zoneSelection = new ZoneSelection(); // Creating an instance of ZoneSelection to handle user input for zone or CSV calculation.
            String zone = zoneSelection.chooseZone(mainScanner); // Getting the selected zone or null if CSV calculation was chosen.

            if (zone != null) { // If a zone was selected, proceed to fetch prices from the API.
               ApiClient apiClient = new ApiClient(); // Creating an instance of ApiClient to handle API requests.
               String jsonData = apiClient.getPrices(zone); // Fetching electricity prices for today and tomorrow for the selected zone.

                Menu menu = new Menu(); // Creating an instance of Menu to display options to the user.
                menu.showMenu(jsonData, mainScanner); // Showing the menu and passing the fetched JSON data and scanner for user interaction.

                System.out.println(jsonData); // Printing the raw JSON data for debugging or informational purposes.
            } else {
                System.out.println("CSV calculation done, exiting program."); // If CSV calculation was chosen, inform the user and exit.
            }
        }
    }
}
