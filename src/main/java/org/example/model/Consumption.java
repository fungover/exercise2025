package org.example.model;

import java.time.ZonedDateTime;

public record Consumption(ZonedDateTime timestamp, double kWh)  {
}

