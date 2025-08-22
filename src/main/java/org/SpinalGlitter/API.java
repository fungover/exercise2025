package org.SpinalGlitter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static java.net.http.HttpClient.newHttpClient;

public class API {
    public static void main(String[] args) throws Exception {

            String zone = "SE3";
            LocalDate today = LocalDate.now(ZoneId.of("Europe/Stockholm"));

            String url = String.format(
                    "https://www.elprisetjustnu.se/api/v1/prices/%d/%s_%s.json",
                    today.getYear(),
                    today.format(DateTimeFormatter.ofPattern("MM-dd")),
                    zone
            );

            System.out.println("GET " + url);

            HttpClient client = newHttpClient();
            HttpRequest req = HttpRequest.newBuilder(URI.create(url))
                    .header("Accept", "application/json")
                    .GET()
                    .build();

            HttpResponse<String> res = client.send(req, HttpResponse.BodyHandlers.ofString());
            System.out.println("Status: " + res.statusCode());

            // Visa bodyn för att se att jag lyckas hämta data
            String body = res.body();
            System.out.println("Body: " + body);



            // Parsea JSON -> List<PriceEntry>
            ObjectMapper om = new ObjectMapper().registerModule(new JavaTimeModule());
            List<PriceEntry> entries = om.readValue(body, new TypeReference<List<PriceEntry>>() {});

            //Visa hur många entrys så att jag får med alla 24 timmar
            System.out.println("Parsed entries: " + entries.size());


            if (!entries.isEmpty()) {
                PriceEntry e0 = entries.getFirst();
                System.out.printf("Test-post: %s – %s  %.4f SEK/kWh%n",
                        e0.timeStart, e0.timeEnd, e0.sekPerKWh);
            }
        }
    }

