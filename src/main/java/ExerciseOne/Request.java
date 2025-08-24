package ExerciseOne;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

//https://www.elprisetjustnu.se/api/v1/prices/[ÅR]/[MÅNAD]-[DAG]_[PRISKLASS].json

public class Request {

    //Todo: Check if possible to get data for coming pricing
    //Todo: Save prices from both days

    public void getDate(int area) throws IOException, InterruptedException {

        String today = formatDate(LocalDate.now());
        String search = today+"_SE"+area;

        request(search);

        LocalTime time = LocalTime.now();
        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("HH");
        String  formatedTime = time.format(dtf2);

        int timeToday = Integer.parseInt(formatedTime);

        if(timeToday >= 13){
            String tomorrow = formatDate(LocalDate.now().plusDays(1));
            String searchTomorrow = tomorrow+"_SE"+area;
            request(searchTomorrow);
        }

        //Spara resultatet i en egen lista?
        //Kolla om kan göra ytterligare anrop och spara det resultatet i en egen variabel
    }

    public void request(String search) throws IOException, InterruptedException {

        String elprisURL = "https://www.elprisetjustnu.se/api/v1/prices/"+search+".json";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(elprisURL))
                .GET()
                .build();

        //client.send när du behöver svaret direkt, enkla anrop
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());

        ObjectMapper mapper = new ObjectMapper();
        Pricing[] prices = mapper.readValue(response.body(), Pricing[].class);

        System.out.println("Total Prices: " + prices.length);
    }

    public String formatDate(LocalDate date){
        return date.format(DateTimeFormatter.ofPattern("yyyy/MM-dd"));
    }

    record Pricing (double SEK_per_kWh, double EUR_per_kWh, double EXR, String time_start, String time_end){}

}
