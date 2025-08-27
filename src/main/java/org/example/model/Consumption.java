/**
 * Data record representing a single consumption entry.
 * -timestamp: ZonedDateTIme when consumption occurred.
 * -kWh: The number of kilowatt-hours consumed in that hour.
 */

package org.example.model;

import java.time.ZonedDateTime;

public record Consumption(ZonedDateTime timestamp, double kWh)  {
}

