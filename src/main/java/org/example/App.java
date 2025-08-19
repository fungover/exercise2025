package org.example;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class App {
    public static void main(String[] args) {
        String URL = "https://www.elprisetjustnu.se/api/v1/prices/2025/08-19_SE3.json";
        getPrices(URL);
    }

    static void getPrices(String url_string) {
        try {
            URL url = new URL(url_string);
            URLConnection connection = url.openConnection();
            InputStream inputStream = connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String response;
            while ((response = bufferedReader.readLine()) != null) {
                System.out.println(response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
