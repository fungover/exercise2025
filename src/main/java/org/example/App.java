package org.example;

import org.example.api.ApiClient;
import org.example.cli.Menu;
import org.example.cli.ZoneSelection;

public class App {
    public static void main(String[] args) throws Exception {

        ZoneSelection zoneSelection = new ZoneSelection();
        String zone = zoneSelection.chooseZone();

        if (zone != null) { // if zone is not null, we proceed with api calls and show the menu for that.

            ApiClient apiClient = new ApiClient();
            String jsonToday = apiClient.getRequest(java.time.LocalDate.now(), zone);
            String jsonTomorrow = apiClient.getRequest(java.time.LocalDate.now().plusDays(1), zone);

            Menu menu = new Menu();
            menu.showMenu(jsonToday, jsonTomorrow);
            System.out.println(jsonTomorrow);
        } else { // If the zone is null, we do the calculation from csv and exit the program.
            System.out.println("CSV calculation done, exiting program.");
        }
    }
}
