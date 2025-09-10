package org.example;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

public class PriceUtilsTest {

    @Test
    void averagePriceShouldReturnCorrectValue() {
        List<PricePoint> prices = List.of(
                new PricePoint("00:00", 1.0),
                new PricePoint("01:00", 2.0),
                new PricePoint("02:00", 3.0)
        );

        double averagePrice = PriceUtils.averagePrice(prices);

        assertEquals(2.0, averagePrice);
    }
}
