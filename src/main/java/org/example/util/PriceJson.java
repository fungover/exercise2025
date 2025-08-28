package org.example.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.model.PricePoint;

import java.io.IOException;
import java.util.List;

public final class PriceJson {
    private static final ObjectMapper MAPPER = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .setSerializationInclusion(JsonInclude.Include.NON_NULL);

    private PriceJson() {
    }

    public static List<PricePoint> parseList(String json) throws IOException {
        return MAPPER.readValue(json, new TypeReference<List<PricePoint>>() {
        });

    }

    public static String toPrettyJson(Object data) throws IOException {
        return MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(data);
    }
}
