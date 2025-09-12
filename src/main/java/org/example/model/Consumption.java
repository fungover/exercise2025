/**
 * Data record representing a single consumption entry.
 * -timestamp: ZonedDateTIme when consumption occurred.
 * -kWh: The number of kilowatt-hours consumed in that hour.
 */

package org.example.model;

import java.time.ZonedDateTime;
import java.util.Objects;

public record Consumption(ZonedDateTime timestamp, double kWh)  {
    public Consumption {
        Objects.requireNonNull(timestamp, "timestamp must not be null");
        if (Double.isNaN(kWh) || kWh < 0d) {
            throw new IllegalArgumentException("kWh must be >= 0");
        }
    }
}

