/*This is a model for the structure of the API so that Jackson can work with the data*/
package org.example.electricity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Instant;

@JsonIgnoreProperties(ignoreUnknown = true) //If something changes in the API, our mapping will still work
public record PriceData(
@JsonProperty("time_start") Instant timeStart,
@JsonProperty("time_end") Instant timeEnd,
@JsonProperty("SEK_per_kWh") double sekPerkWh,
@JsonProperty("EUR_per_kWh") double eurPerkWh,
@JsonProperty("EXR") double exchangeRate
)
{}

