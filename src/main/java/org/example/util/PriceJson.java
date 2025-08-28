package org.example.util;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.model.DailyChargeWindows;
import org.example.model.DailyExtremes;
import org.example.model.HourExtremes;
import org.example.model.PricePoint;
import java.io.IOException;
import java.util.List;

public final class PriceJson {
    private PriceJson() {}

    private static final ObjectMapper MAPPER = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .setSerializationInclusion(JsonInclude.Include.NON_NULL);

    public static List<PricePoint> parseList(String json) throws IOException {
        return MAPPER.readValue(json, new TypeReference<List<PricePoint>>() {});
    }

    public static String toPrettyJson(List<PricePoint> data) throws IOException {
        return MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(data);
    }

    public static String toPrettyJson(HourExtremes data) throws IOException {
        return MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(data);
    }

    public static String toPrettyJson(DailyExtremes data) throws IOException {
        return MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(data);
    }

    public static String toPrettyJson(DailyChargeWindows data) throws IOException {
        return MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(data);
    }
}
