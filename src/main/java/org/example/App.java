package org.example;
import org.example.api.PriceApiClient;
import org.example.menus.OptionMenu;
import org.example.services.PriceService;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Scanner;

//TODO List
//Create boilerplate for project
//Setup menu for Area selection
//Setup main menu selection (A exit option should be included)
// Setup URL Builder
// Setup API request for electricity prices data
// Setup error handling for failed http request etc
//Todo: Implement mean price calculation for current 24h
//Todo: Implement logic to find cheapest and most expensive hour (earliest if tie)
//Todo: Implement sliding window algorithm for best charging time (2, 4, 8 hours)
//Todo: Format and print results clearly to user
//Todo: (Optional) Add CSV import for consumption data and calculate total cost
// TODO: Later, replace JSON print with a nice informative message

public class App {
    private static final String[] AREAS = {
            "Luleå / Northern Sweden",
            "Sundsvall / Northern Central Sweden",
            "Stockholm / Southern Central Sweden",
            "Malmö / Southern Sweden"
    };

    private static final String[] OPTIONS = {
            "Download prices for today/next day",
            "Print mean price for current 24h",
            "Show cheapest & most expensive hour",
            "Best charging window (2/4/8h)",
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

        ZoneId zone = ZoneId.of("Europe/Stockholm");
        LocalDate date = LocalDate.now(zone);

        PriceApiClient apiClient = new PriceApiClient();
        PriceService service = new PriceService(apiClient, zone);

        try {
            String result = service.handleChoice(mainMenuChoice, date, areaCode);
            System.out.println(result);
        } catch (Exception e) {
            System.err.println("Something went wrong while fetching electricity prices. Please try again later.");
        }
    }
}
