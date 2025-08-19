package org.example;

import org.example.api.ApiClient;
import org.example.cli.ZoneSelection;
import org.example.utils.CalculateMeanPrice;

import java.time.LocalDate;

public class App {
    public static void main(String[] args) throws Exception {

        ZoneSelection zoneSelection = new ZoneSelection();
        String zone = zoneSelection.chooseZone();

        ApiClient api = new ApiClient();
        String jsonToday = api.getRequest(LocalDate.now(), zone);
        String jsonTomorrow = api.getRequest(LocalDate.now().plusDays(1), zone);

        System.out.println(jsonToday);

        System.out.println(CalculateMeanPrice.meanSEK(jsonToday));




    }
}
