package org.example;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    void cheapestPriceShouldReturnLowestPricePoint() {
        List<PricePoint> prices = List.of(
                new PricePoint("00:00", 1.0),
                new PricePoint("01:00", 0.8),
                new PricePoint("02:00", 3.0)
        );

        PricePoint cheapestPrice = PriceUtils.cheapestPrice(prices);

        assertEquals("01:00", cheapestPrice.getHour());
        assertEquals(0.80, cheapestPrice.getPrice());
    }

    @Test
    void cheapestPriceOnEmptyListShouldThrowException() {
        List<PricePoint> empty = List.of();
        assertThrows(IllegalArgumentException.class, () -> PriceUtils.cheapestPrice(empty));
    }

    @Test
    void mostExpensivePriceShouldReturnHighestPricePoint() {
        List<PricePoint> prices = List.of(
                new PricePoint("00:00", 1.0),
                new PricePoint("01:00", 0.8),
                new PricePoint("02:00", 3.0)
        );

        PricePoint mostExpensivePrice = PriceUtils.mostExpensivePrice(prices);

        assertEquals("02:00", mostExpensivePrice.getHour());
        assertEquals(3.0, mostExpensivePrice.getPrice());
    }

    @Test
    void mostExpensivePriceOnEmptyListShouldThrowException() {
        List<PricePoint> empty = List.of();
        assertThrows(IllegalArgumentException.class, () -> PriceUtils.mostExpensivePrice(empty));
    }
}
