package org.example.client;

import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;

public record PriceDto (
@SerializedName("time_start") String timeStart,
@SerializedName("time_end") String timeEnd,
@SerializedName("SEK_per_kWh") BigDecimal sekPerKWh
) {}
