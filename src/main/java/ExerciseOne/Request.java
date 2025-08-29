package ExerciseOne;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

//https://www.elprisetjustnu.se/api/v1/prices/[ÅR]/[MÅNAD]-[DAG]_[PRISKLASS].json

public class Request {
    private static final ObjectMapper MAPPER = new ObjectMapper()
            .findAndRegisterModules()
            .configure(com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    private static final HttpClient CLIENT = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    public List<Pricing> request(String search) throws IOException, InterruptedException {
        String elprisURL = "https://www.elprisetjustnu.se/api/v1/prices/"+search+".json";

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(elprisURL))
                    .timeout(Duration.ofSeconds(10))
                    .header("Content-Type", "application/json")
                    .header("User-Agent", "exercise2025-cli/1.0")
                    .GET()
                    .build();

            HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

            String body = response.body();
            String check = body == null ? "" : body.stripLeading();
            if(response.statusCode() == 200 && check.startsWith("[")) {
                return MAPPER.readValue(check, new TypeReference<List<Pricing>>() {});
            }

            return List.of();

    }
}
