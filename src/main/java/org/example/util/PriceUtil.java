package org.example.util;
import org.example.model.MeanPrice;
import org.example.model.HourlyPrice;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PriceUtil {
    public static MeanPrice calculateMeanPrice(List<HourlyPrice> prices) {
        if (prices.isEmpty()) return new MeanPrice(0.0, 0.0);
        double sumSek = prices.stream().mapToDouble(HourlyPrice::getSekPerKwh).sum();
        double sumEur = prices.stream().mapToDouble(HourlyPrice::getEurPerKwh).sum();
        return new MeanPrice(sumSek / prices.size(), sumEur / prices.size());
    }

public static List<HourlyPrice> convertToHourlyPrices(List<Object> priceData, String zone) {

    List<HourlyPrice> prices = new ArrayList<>();

    for (Object o : priceData)  {
    Map<String, Object> map = (Map<String, Object>) o;
    int hour = parseHour((String) map.get("time_start"));
        double sekPerKwh = ((Number) map.get("SEK_per_kWh")).doubleValue();
        double eurPerKwh = ((Number) map.get("EUR_per_kWh")).doubleValue();
        prices.add(new HourlyPrice(hour, sekPerKwh, eurPerKwh, zone));
    }
            return prices;
    }

    public static String formatPrice(double price) {
        return String.format("%.2f", price);
    }

    private static int parseHour(String timeStart) {
        return Integer.parseInt(timeStart.split("T")[1].split(":")[0]);
    }
}
