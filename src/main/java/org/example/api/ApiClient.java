package org.example.api;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;

public class ApiClient {

    public String getRequest(LocalDate date, String zone) throws Exception { //Parameter taken from method call from user input (for example SE1, SE2, SE3 etc..).

        String url = String.format( //Using string format to create the url with year, month day and zone so its dynamic. (eg %02d for month and day.)
                "https://www.elprisetjustnu.se/api/v1/prices/%d/%02d-%02d_%s.json",
                date.getYear(), date.getMonthValue(), date.getDayOfMonth(), zone
        );

        HttpClient client = HttpClient.newHttpClient(); //Creating a new Httpclient to make the request.
        HttpRequest req = HttpRequest.newBuilder(URI.create(url)).GET().build(); // Creating a new HttpRequest with the url and setting it to GET.
        HttpResponse<String> resp = client.send(req, HttpResponse.BodyHandlers.ofString()); // Sending the request and getting the response as a string.

        if (resp.statusCode() == 404) return "[]"; // if the response is 404, we return empty JSON to avoid that the program crashes.
        if (resp.statusCode() != 200) throw new RuntimeException("HTTP " + resp.statusCode()); // if the response is not 200, we throw exception with status code.

        return resp.body(); // Returning the body of the response as a string, which is in the JSON format.
    }
}