package org.example;

import org.example.api.ApiClient;
import org.example.cli.Menu;
import org.example.cli.ZoneSelection;

public class App {
    public static void main(String[] args) throws Exception {

        ZoneSelection zoneSelection = new ZoneSelection();
        String zone = zoneSelection.chooseZone();

        if (zone != null) { // if the zone is not null, we proceed with api calls and show the menu for that.

            ApiClient apiClient = new ApiClient();
            String jsonData = apiClient.getPrices(java.time.LocalDate.now(), zone); // Getting today's data from API.

            Menu menu = new Menu();
            menu.showMenu(jsonData);
            System.out.println(jsonData);
        } else { // If the zone is null, we do the calculation from csv and exit the program.
            System.out.println("CSV calculation done, exiting program.");
        }
    }
}
