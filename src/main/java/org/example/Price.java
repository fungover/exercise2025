package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.ArrayUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Price {
    private Properties[] todayPrices;
    private Properties[] tomorrowPrices;

    public Price() {
        FetchPrice fetch = new FetchPrice();
        ObjectMapper mapper = new ObjectMapper();

        //Get electricity prices for today
        try {
            String firstPayload = fetch.getTodayPrices();
            todayPrices = mapper.readValue(firstPayload, Properties[].class);
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
        }

        //Get electricity prices for tomorrow if available
        try {
            String secondPayload = fetch.getTomorrowPrices();
            tomorrowPrices = mapper.readValue(secondPayload, Properties[].class);
        } catch (JsonProcessingException _) {
        }
    }

    public void getMeanPrice() {
        BigDecimal totalSek = BigDecimal.ZERO;
        BigDecimal totalEur = BigDecimal.ZERO;
        int count = todayPrices.length;

        for (var price : todayPrices) {
            totalSek = totalSek.add(price.getSekPerKWh());
            totalEur = totalEur.add(price.getEurPerKWh());
        }

        BigDecimal meanPriceSek = totalSek.divide(BigDecimal.valueOf(count),
                RoundingMode.HALF_UP);
        BigDecimal meanPriceEur = totalEur.divide(BigDecimal.valueOf(count),
                RoundingMode.HALF_UP);

        System.out.println("The mean electricity price today:");
        System.out.println("SEK: " + meanPriceSek);
        System.out.println("EUR: " + meanPriceEur);
    }

    public void getBottomPrice() {
        Properties prop = new Properties();
        Properties[] prices;
        if (tomorrowPrices != null) {
            prices = ArrayUtils.addAll(todayPrices, tomorrowPrices);
        } else {
            prices = todayPrices;
        }

        BigDecimal[] priceArray = new BigDecimal[prices.length];
        for  (int i = 0; i < prices.length; i++) {
            priceArray[i] = prices[i].getSekPerKWh();
        }
        BigDecimal lowestVal = prop.getMinValue(priceArray);

        // The strings of text in Properties[], all have 25 characters each.
        String dateStart = "";
        String dateEnd = "";
        String timeStart = "";
        String timeEnd = "";
        for (var price : prices) {
            if (lowestVal.compareTo(price.getSekPerKWh()) == 0) {
                dateStart = price.getTimeStart().substring(0, 10);
                dateEnd = price.getTimeEnd().substring(0, 10);
                timeStart = price.getTimeStart().substring(11, 16);
                timeEnd = price.getTimeEnd().substring(11, 16);
            }
        }
        System.out.println(dateStart + " " + timeStart + "\n"
                + dateEnd + " " + timeEnd);
    }
}