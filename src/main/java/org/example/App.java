package org.example;

import org.example.api.PriceApiClient;
import org.example.menus.OptionMenu;
import org.example.services.PriceService;
import org.example.ui.ResultViews;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Scanner;

public class App {
    private static final String[] AREAS = {
            "Luleå / Northern Sweden",
            "Sundsvall / Northern Central Sweden",
            "Stockholm / Southern Central Sweden",
            "Malmö / Southern Sweden"
    };

    private static final String[] OPTIONS = {
            "Download prices for today/tomorrow",
            "Print mean price for current 24h",
            "Show cheapest & most expensive hour for today and tomorrow",
            "Best charging window (2/4/8h) for today and tomorrow",
    };

    public static void main(String[] args) {
        System.out.println("Welcome to my Command-Line Interface that helps you optimize your electricity consumption and reduce costs!\n");
        Scanner scanner = new Scanner(System.in);

        OptionMenu areaMenu = new OptionMenu(scanner, AREAS);
        int areaMenuChoice = areaMenu.getOption();
        areaMenu.menuExit(areaMenuChoice);
        String areaCode = "SE" + areaMenuChoice;

        OptionMenu mainMenu = new OptionMenu(scanner, OPTIONS);
        int mainMenuChoice = mainMenu.getOption();
        mainMenu.menuExit(mainMenuChoice);
        scanner.nextLine();

        ZoneId zone = ZoneId.of("Europe/Stockholm");
        LocalDate date = LocalDate.now(zone);

        PriceApiClient apiClient = new PriceApiClient();
        PriceService service = new PriceService(apiClient, zone);

        try {
            String result = service.handleChoice(mainMenuChoice, date, areaCode);
            String pretty = switch (mainMenuChoice) {
                case 1 -> ResultViews.prices(result, zone, date, areaCode, scanner);
                case 2 -> ResultViews.mean(result, date, areaCode);
                case 3 -> ResultViews.extremes(result, zone, date, areaCode);
                case 4 -> ResultViews.chargeWindows(result, zone, date, areaCode);
                default -> result;
            };
            System.out.println(pretty);

        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
            System.err.println("Operation interrupted. Please retry.");
            System.err.println("Details: " + ie.getMessage());
        } catch (Exception e) {
            System.err.println("Failed to fetch/process prices: " + e.getMessage());
        }
    }
}
