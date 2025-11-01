package org.example.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public final class JsonMapper {

    private static final ObjectMapper mapper = new ObjectMapper()  // Objectmapper is used to convert JSON to Java objects and vice versa.
            .registerModule(new JavaTimeModule()) // Supports Java 8 date and time types (e.g., LocalDate, LocalDateTime, OffsetDateTime).
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false) // ingore unknown JSON fields (e.g., EXR if its not in our record.)
            .disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE) // Makes sure we dont adjust times and keeps the original time zone information from the JSON.
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // When writing dates, use ISO-8601 format instead of timestamps (e.g., 2023-10-01T00:00:00+02:00 instead of 1696118400000).

    private JsonMapper() {} // private constructor to prevent instantiation of this utility class.

    public static ObjectMapper get() {
        return mapper.copy(); // return a copy of the ObjectMapper instance, so that the original instance remains unchanged.
    }
}

