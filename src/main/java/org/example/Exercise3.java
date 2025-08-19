package org.example;

import java.util.Scanner;

public class Exercise3 {
    static void main() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Ange första heltalet: ");
        if (!scanner.hasNextInt()) {
            System.out.println("Skärp dig, det skulle vara en siffra");
            return;
        }
        int tal1 = scanner.nextInt();

        System.out.println("Ange andra heltalet: ");
        if (!scanner.hasNextInt()) {
            System.out.println("... det skulle vara ett heltal!");
            return;
        }
        int tal2 = scanner.nextInt();

        if (tal1 > tal2) {
            System.out.println(tal1 + " är större än " + tal2);
        } else if (tal2 > tal1) {
            System.out.println(tal2 + " är större än " + tal1);
        } else {
            System.out.println("Talen är lika stora");
        }
        
    }
    /*Vacker skrivit<3, jag tänkte exakt samme, men double,
    men de hade ju varit fel. Pga "Heltal"*/
}
/*
Uppgift 3: Jämför två tal Användaren matar
in två heltal Programmet skriver ut vilket tal
 som är störst, eller om de är lika
 */