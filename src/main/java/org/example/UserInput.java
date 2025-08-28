package org.example;

import java.util.Locale;
import java.util.regex.Pattern;

public class UserInput {

    private static final Pattern AREA = Pattern.compile("SE[1-4]");

    public static String getValidatedPriceArea() {
        if (System.console() == null) {
            System.out.println("\nIngen console tillgänglig (IDE?), använder default: SE3");
            return "SE3";
        }

        while (true) {
            String input = System.console().readLine("\nAnge önskad zon (SE1, SE2, SE3 eller SE4): ");
            if (input == null) {
                // EOF or console closed: exit gracefully with default or by returning null. We'll default to SE3.
                System.out.println("\nInget mer användarinput (EOF). Använder default: SE3");
                return "SE3";
            }

            String priceArea = input.trim().toUpperCase(Locale.ROOT);

            if (AREA.matcher(priceArea).matches()) {
                return priceArea;
            }
            
            System.out.println("Ogiltig zon '" + input + "'! Försök igen.");
        }
    }
}