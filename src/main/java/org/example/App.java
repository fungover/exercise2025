package org.example;

import java.io.IOException;

public class App {
     static void main(String[] args) {
        try {
            String year = "2025";
            String month = "08";
            String day = "20";
            String priceArea = "SE2";

            String body = ApiClient.fetchPrices(year, month, day, priceArea);
            System.out.println(body);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
            System.err.println("The request was interrupted: " + ie.getMessage());
        } catch (IOException ioe) {
            System.err.println("Network/IO error: " + ioe.getMessage());
        }
    }
}
