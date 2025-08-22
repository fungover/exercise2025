package ExerciseOne;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;

//https://www.elprisetjustnu.se/api/v1/prices/[ÅR]/[MÅNAD]-[DAG]_[PRISKLASS].json

public class Request {

    String idag = LocalDate.now().toString();
    String imorgon = LocalDate.now().plusDays(1).toString();


    static void request(int area){

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://www.elprisetjustnu.se/api/v1/prices/2025/08-2"+area+"_SE1.json"))
                .build();
        var send = client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(System.out::println)
                .join();

    }

}
