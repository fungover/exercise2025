package org.example;

import org.example.api.ApiClient;
import org.example.cli.ZoneSelection;

import java.time.LocalDate;

public class App {
    public static void main(String[] args) throws Exception {

        ZoneSelection zoneSelection = new ZoneSelection();
        String zone = zoneSelection.chooseZone();

        ApiClient api = new ApiClient();
        String jsonToday = api.getRequest(LocalDate.now(), zone);
        System.out.println(jsonToday);

    }
}
