package org.example.utils;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CalculateMeanPrice {

public static BigDecimal meanSEK(String json) {
    return calculateMean(json, "SEK_per_kWh");
}
public static BigDecimal meanEUR(String json) {
    return calculateMean(json, "EUR_per_kWh");
}

private static BigDecimal calculateMean(String json, String currency) {
    try {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonArray = mapper.readTree(json);

        BigDecimal sum = BigDecimal.ZERO;
        int count = 0;

        for (JsonNode entry : jsonArray) {
            BigDecimal price = entry.get(currency).decimalValue();
            sum = sum.add(price);
            count++;
        }
        if (count == 0) return BigDecimal.ZERO;

        return sum.divide(BigDecimal.valueOf(count), 5, RoundingMode.HALF_UP);
    } catch (Exception e) {
        System.err.println("Error: " + e.getMessage());
        return BigDecimal.ZERO;
    }
}



}

