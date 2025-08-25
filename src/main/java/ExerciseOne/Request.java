package ExerciseOne;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

//https://www.elprisetjustnu.se/api/v1/prices/[ÅR]/[MÅNAD]-[DAG]_[PRISKLASS].json

public class Request {

    public void request(String search) throws IOException, InterruptedException {

        String elprisURL = "https://www.elprisetjustnu.se/api/v1/prices/"+search+".json";

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(elprisURL))
                    .GET()
                    .build();

            //client.send när du behöver svaret direkt, enkla anrop
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            PricesList pricesList = new PricesList();
            pricesList.list(response);

    }

}
