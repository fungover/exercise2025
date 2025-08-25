package org.example.electricity;

/*This is a model for the structure of the API*/

import java.time.Instant;

public record PriceData
        (Instant timeStart,
         Instant timeEnd,
         double sekPerKWh,
         double eurPerKWh,
         double exchangeRate) {
}

