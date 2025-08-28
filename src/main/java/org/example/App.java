package org.example;

public class App {

        public static void main(String[] args) throws Exception {
            ElprisApiClient api = new ElprisApiClient();

            // Example:
            String json = api.fetchRaw(2025, "08-27", "SE3");

            System.out.println("Got JSON length: " + json.length());
            System.out.println(json.substring(0, 200) + "...");
        }
    }

