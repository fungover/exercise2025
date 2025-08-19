package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.api.ElprisApi;
import org.example.cli.ZonePicker;
import org.example.util.ElectricityPrice;

import java.time.LocalDate;
import java.util.List;

public class App {
    public static void main(String[] args) throws Exception {
        ElprisApi api = new ElprisApi();
        String zone = new ZonePicker().getZone();
        String today = api.getRequest(LocalDate.now(), zone);
        String tomorrow = api.getRequest(LocalDate.now().plusDays(1), zone);

        ObjectMapper mapper = new ObjectMapper();
        List<ElectricityPrice> pricesToday = mapper.readValue(today,
          new TypeReference<>() {
          });
        List<ElectricityPrice> pricesTomorrow = mapper.readValue(today,
          new TypeReference<>() {
          });

        priceCheck(pricesToday);


    }

    public static void priceCheck(List<ElectricityPrice> prices) {
        for (ElectricityPrice price : prices) {
            System.out.printf("%s -> %.3f SEK/kWh%n",
              price.time_start.substring(11, 16), price.SEK_per_kWh);
        }
    }
}
