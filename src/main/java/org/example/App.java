package org.example;

import org.example.api.ApiClient;
import org.example.cli.Menu;
import org.example.cli.ZoneSelection;

import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {

        try (Scanner mainScanner = new Scanner(System.in)) {

            ZoneSelection zoneSelection = new ZoneSelection();
            String zone = zoneSelection.chooseZone(mainScanner);

            if (zone != null) {
               ApiClient apiClient = new ApiClient();
               String jsonData = apiClient.getPrices(java.time.LocalDate.now(), zone);

                Menu menu = new Menu();
                menu.showMenu(jsonData, mainScanner);

                System.out.println(jsonData);
            } else {
                System.out.println("CSV calculation done, exiting program.");
            }
        }
    }
}
