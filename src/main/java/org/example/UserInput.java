package org.example;

public class UserInput {
    
    public static String getValidatedPriceArea() {
        if (System.console() == null) {
            System.out.println("\nIngen console tillgänglig (IDE?), använder default: SE3");
            return "SE3";
        }
        
        while (true) {
            String input = System.console().readLine("\nAnge önskad zon (SE1, SE2, SE3 eller SE4): ");
            String priceArea = input.trim().toUpperCase();
            
            if (priceArea.matches("SE[1-4]")) {
                return priceArea;
            }
            
            System.out.println("Ogiltig zon '" + input + "'! Försök igen.");
        }
    }
}