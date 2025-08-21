package org.example;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

public class Client {
    public static void main(String[] args) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://www.elprisetjustnu.se/api/v1/prices/2025/08-21_SE3.json"))
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            int statusCode = response.statusCode();
            String data = (statusCode == 200) ? response.body() : null;
            /*System.out.println(statusCode);
            System.out.println(data);*/

            if (data != null) {
                convertToJson(data);
            }
        } catch (Exception e) {
            /*throw new RuntimeException(e);*/
            System.out.println("Something went wrong: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     *
     * @param jsonData
     * @return
     */
    static void convertToJson(String jsonData) {
        System.out.println(jsonData);
        /*try {
            ObjectMapper mapper = new ObjectMapper();
            List<Map<String, Object>> jsonList = mapper.readValue(jsonData, new TypeReference<>() {});
            for (Map<String, Object> item : jsonList) {
                System.out.println("Starttid: " + item.get("time_start"));
                System.out.println("Sluttid: " + item.get("time_end"));
                System.out.println("SEK/kWh: " + item.get("SEK_per_kWh"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }
}
