package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Area {

    public static String chooseArea() throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("---- Välj område genom att skriva in en av siffrorna i menyn, sedan tryck ENTER ----");
        System.out.println("1: Luleå / Norra Sverige");
        System.out.println("2: Sundsvall / Norra Mellansverige");
        System.out.println("3: Stockholm / Södra Mellansverige");
        System.out.println("4: Malmö / Södra Sverige");


        while (true) {
            try {
                int input = Integer.parseInt(br.readLine());
                switch (input) {
                    case 1 -> {return "SE1"; }
                    case 2 -> {return "SE2"; }
                    case 3 -> {return "SE3"; }
                    case 4 -> {return "SE4"; }
                    default -> System.out.println("Du måste välja ett av alternativen i menyn.");
                }

            } catch (NumberFormatException e) {
                System.out.println("❌ Du måste skriva en siffra (1–4).");
            }

        }
    }
}
