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

     BigDecimal sum = BigDecimal.ZERO;
     int count = 0;

     for (ElectricityPrice price : prices) {
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

};
}

