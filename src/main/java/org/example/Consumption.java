package org.example;

import java.time.ZonedDateTime;

public record Consumption(ZonedDateTime timestamp, double kWh)  {
}

