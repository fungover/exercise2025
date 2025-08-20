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
            /*int status = client.sendAsync(request, BodyHandlers.ofString())
                    .thenApply(HttpResponse::statusCode)
                    .join();

            String data = null;
            if (status == 200) {
                data = client.sendAsync(request, BodyHandlers.ofString())
                        .thenApply(HttpResponse::body)
                        .join();
            }*/
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            int statusCode = response.statusCode();
            String data = (statusCode == 200) ? response.body() : null;
            System.out.println(statusCode);
            System.out.println(data);
        } catch (Exception e) {
            /*throw new RuntimeException(e);*/
            System.out.println("Something went wrong: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
