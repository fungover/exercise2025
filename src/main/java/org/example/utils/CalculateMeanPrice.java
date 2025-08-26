package org.example.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.ElectricityPrice;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class CalculateMeanPrice {

public static BigDecimal meanSEK(String json) {
    return calculateMean(json, "SEK_per_kWh");
}
public static BigDecimal meanEUR(String json) {
    return calculateMean(json, "EUR_per_kWh");
}

private static BigDecimal calculateMean(String json, String currency) {

 try {
     ObjectMapper mapper = JsonMapper.get();
     List<ElectricityPrice> prices = mapper.readValue(json, new TypeReference<>() {});

     if (prices.isEmpty()) {
         return BigDecimal.ZERO;
     }

     int startIndex = PriceDataUtils.findCurrentOrNextHourIndex(prices);

     int availableHours = prices.size() - startIndex;
     int hoursToInclude = Math.min(24, availableHours);

     if (hoursToInclude == 0) {
         return BigDecimal.ZERO;
     }

     BigDecimal sum = BigDecimal.ZERO;
     int count = 0;

     for (int i = startIndex; i < startIndex + hoursToInclude; i++) {
         ElectricityPrice price = prices.get(i);
         if ("SEK_per_kWh".equals(currency)) {
             sum = sum.add(price.sekPerKwh());
         } else if ("EUR_per_kWh".equals(currency)) {
             sum = sum.add(price.eurPerKwh());
         }
         count++;
     }
     return sum.divide(BigDecimal.valueOf(count), 5, RoundingMode.HALF_UP);
 } catch (Exception e) {
     return BigDecimal.ZERO;
 }

}

public static String getPeriodInfo(String json) {
    try {
        ObjectMapper mapper = JsonMapper.get();
        List<ElectricityPrice> prices = mapper.readValue(json, new TypeReference<>() {});

        int startIndex = PriceDataUtils.findCurrentOrNextHourIndex(prices);
        int hoursToInclude = Math.min(24, prices.size() - startIndex);

        if (hoursToInclude <= 0) return "No data available";

        return String.format("from %s to %s (%d hours)\n",
                prices.get(startIndex).timeStart().toLocalDateTime().toLocalTime(),
                prices.get(startIndex + hoursToInclude - 1).timeEnd().toLocalDateTime().toLocalTime(),
                hoursToInclude);
    } catch (Exception e) {
        return "Error retrieving period info";
    }
}
}

