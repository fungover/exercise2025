package cheap.electricity.services;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class Api {
  private final String apiUrl;

  public Api(String apiUrl){
    this.apiUrl = apiUrl;
  }

  public void showPrices() throws IOException, InterruptedException {
    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(this.apiUrl))
            .timeout(Duration.ofSeconds(10))
            .header("Accept", "application/json")
            .build();

    var response = client.send(request, HttpResponse.BodyHandlers.ofString());
    String json = response.body();
    System.out.println(json);
  }
}


