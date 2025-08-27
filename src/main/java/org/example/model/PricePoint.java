package org.example.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public record PricePoint(
        @JsonProperty("time_start")  OffsetDateTime start,
        @JsonProperty("time_end")    OffsetDateTime end,
        @JsonProperty("SEK_per_kWh") BigDecimal sekPerKWh
) {}
