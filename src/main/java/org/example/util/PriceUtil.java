package org.example.util;
import com.fasterxml.jackson.jr.ob.JSON;
import org.example.model.MeanPrice;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class PriceUtil {

    public static MeanPrice calculateMeanPrice(URL url) throws Exception {
        List<Object> results = JSON.std.listFrom(url);

        double sumSek = 0.0;
        double sumEur = 0.0;

        for (Object o : results) {
            Map<String, Object> map = (Map<String, Object>) o;
            sumSek += ((Number) map.get("SEK_per_kWh")).doubleValue();
            sumEur += ((Number) map.get("EUR_per_kWh")).doubleValue();
        }

        double meanSek = sumSek / results.size();
        double meanEur = sumEur / results.size();

        return new MeanPrice(meanSek, meanEur);
    }

}
