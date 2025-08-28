package org.example;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.DecimalFormat;
import java.time.*;
import java.util.Arrays;

public class Price {
    private final DecimalFormat dFormat = new DecimalFormat("00");
    private final LocalDate today = LocalDate.now();
    private final String year = String.valueOf(Year.now().getValue());
    private final String month = dFormat.format(Month.from(today).getValue());
    private final String day = dFormat.format(MonthDay.now().getDayOfMonth());

    String uri = "https://www.elprisetjustnu.se/api/v1/prices/"
                + year + "/" + month + "-" + day + "_SE3.json";

    HttpClient client =  HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(uri))
            .header("Content-Type", "application/json")
            .build();

    public Price() throws IOException, InterruptedException {
        var response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String payload = response.body();

        ObjectMapper mapper = new ObjectMapper();
        Properties[] prices = mapper.readValue(payload, Properties[].class);
        System.out.println(Arrays.toString(prices));
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
class Properties {
    @JsonProperty("SEK_per_kWh")
    private double sekPerKWh;

    @JsonProperty("EUR_per_kWh")
    private double eurPerKWh;

    @JsonProperty("EXR")
    private double exr;

    @JsonProperty("time_start")
    private String timeStart;

    @JsonProperty("time_end")
    private String timeEnd;

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public double getExr() {
        return exr;
    }

    public void setExr(double exr) {
        this.exr = exr;
    }

    public double getEurPerKWh() {
        return eurPerKWh;
    }

    public void setEurPerKWh(double eurPerKWh) {
        this.eurPerKWh = eurPerKWh;
    }

    public double getSekPerKWh() {
        return sekPerKWh;
    }

    public void setSekPerKWh(double sekPerKWh) {
        this.sekPerKWh = sekPerKWh;
    }
}