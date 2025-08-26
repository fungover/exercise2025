package org.example.api;
// this will fetch data from API
//GET https://www.elprisetjustnu.se/api/v1/prices/[ÅR]/[MÅNAD]-[DAG]_[PRISKLASS].json

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;

public class ElprisApi {


    public String getRequest(LocalDate date, String zone) throws Exception {
        /*here we are formating the string to get our year,month,day and
         zone, dynamic */
        String url = String.format(
          "https://www.elprisetjustnu.se/api/v1/prices/%d/%02d-%02d_%s.json",
          date.getYear(), date.getMonthValue(), date.getDayOfMonth(), zone);

        //here we will make our request
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(URI.create(url)).build();
        HttpResponse<String> response = client.send(request,
          HttpResponse.BodyHandlers.ofString());

        //check if response is 404, and return empty JSON
        if (response.statusCode() == 404) return "[]";
        //check if response is NOT 200 we return exeption + status Code
        if (response.statusCode() != 200) throw new RuntimeException(
          "Failed : HTTP error code : " + response.statusCode());

        //returns our response as a String in JSON format
        return response.body();
    }


}
