package org.example;

import org.example.cli.ZoneSelection;

public class App {
    public static void main(String[] args) {

        ZoneSelection zoneSelection = new ZoneSelection();
        String zone = zoneSelection.chooseZone();
        System.out.println("You have selected zone: " + zone);

    }
}
