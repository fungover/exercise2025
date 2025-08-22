package ExerciseOne;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;

//https://www.elprisetjustnu.se/api/v1/prices/[ÅR]/[MÅNAD]-[DAG]_[PRISKLASS].json

public class Request {

    //Todo: Check if possible to get data for coming pricing
    //Todo: Save prices from both days

    String today = LocalDate.now().toString();
    String tomorrow = LocalDate.now().plusDays(1).toString();

    String URL = "";

    public void request(int area) throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://www.elprisetjustnu.se/api/v1/prices/2025/08-22_SE"+area+".json"))
                .GET()
                .build();

        //client.send när du behöver svaret direkt, enkla anrop
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());

        ObjectMapper mapper = new ObjectMapper();
        Pricing[] prices = mapper.readValue(response.body(), Pricing[].class);

        System.out.println("Total Prices: " + prices.length);
    }

    record Pricing (double SEK_per_kWh, double EUR_per_kWh, double EXR, String time_start, String time_end){}

}
