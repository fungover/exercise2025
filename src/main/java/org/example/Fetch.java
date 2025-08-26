package org.example;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

class Fetch {
    public void fetch() throws IOException, InterruptedException {
        String url = "https://www.elprisetjustnu.se/api/v1/prices/2025/08-21_SE3.json";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        JsonElement jsonElement = JsonParser.parseString(response.body());

        String prettyJsonString = gson.toJson(jsonElement);

        System.out.println(prettyJsonString);
    }
}