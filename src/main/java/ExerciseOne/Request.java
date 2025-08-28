package ExerciseOne;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

//https://www.elprisetjustnu.se/api/v1/prices/[ÅR]/[MÅNAD]-[DAG]_[PRISKLASS].json

public class Request {

    public List<Pricing> request(String search) throws IOException, InterruptedException {

        String elprisURL = "https://www.elprisetjustnu.se/api/v1/prices/"+search+".json";

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(elprisURL))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            List<Pricing> responseList = new ArrayList<>();

            if(response.statusCode() == 200 && response.body().startsWith("[")){
                ObjectMapper mapper = new ObjectMapper();
                List<Pricing> pricing = mapper.readValue(response.body(), new TypeReference<List<Pricing>>() {});
                responseList.addAll(pricing);
            }

            return responseList;

    }
}
