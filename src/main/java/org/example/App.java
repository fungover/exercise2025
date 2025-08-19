package org.example;

import org.example.api.ElprisApi;
import org.example.cli.ZonePicker;

import java.time.LocalDate;

public class App {
    public static void main(String[] args) throws Exception {
        ElprisApi api = new ElprisApi();
        String zone = new ZonePicker().getZone();
        String response = api.getRequest(LocalDate.now(), zone);
        System.out.println(response);


    }
}
