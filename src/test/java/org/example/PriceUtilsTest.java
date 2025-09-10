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

    @Test
    void findBestWindowStartShouldReturnCheapestFor2hWindow() {
        List<PricePoint> prices = List.of(
                new PricePoint("00:00", 1.0),
                new PricePoint("01:00", 0.8),
                new PricePoint("02:00", 3.0),
                new PricePoint("03:00", 2.0),
                new PricePoint("04:00", 1.5)
        );

        int bestWindowStart = PriceUtils.findBestWindowStart(prices, 2);

        assertEquals(0, bestWindowStart);
    }

    @Test
    void findBestWindowStartShouldReturnCheapestFor4hWindow() {
        List<PricePoint> prices = List.of(
                new PricePoint("00:00", 1.0),
                new PricePoint("01:00", 0.8),
                new PricePoint("02:00", 3.0),
                new PricePoint("03:00", 2.0),
                new PricePoint("04:00", 1.5)
        );

        int bestWindowStart = PriceUtils.findBestWindowStart(prices, 4);

        assertEquals(0, bestWindowStart);
    }

    @Test
    void findBestWindowStartShouldReturnCheapestFor8hWindow() {
        List<PricePoint> prices = List.of(
                new PricePoint("00:00", 1.0),
                new PricePoint("01:00", 1.8),
                new PricePoint("02:00", 1.0),
                new PricePoint("03:00", 2.0),
                new PricePoint("04:00", 1.5),
                new PricePoint("05:00", 1.2),
                new PricePoint("06:00", 1.0),
                new PricePoint("07:00", 0.8),
                new PricePoint("08:00", 1.5),
                new PricePoint("09:00", 1.2)
        );

        int bestWindowStart = PriceUtils.findBestWindowStart(prices, 8);

        assertEquals(2, bestWindowStart);
    }

    @Test
    void findBestWindowStartShouldPreferFirstBlockWhenTie() {
        List<PricePoint> prices = List.of(
                new PricePoint("00:00", 0.8),
                new PricePoint("01:00", 1.0),
                new PricePoint("02:00", 3.0),
                new PricePoint("03:00", 1.0),
                new PricePoint("04:00", 0.8)
        );

        int bestWindowStart = PriceUtils.findBestWindowStart(prices, 3);

        assertEquals(0, bestWindowStart);
    }

    @Test
    void findBestWindowStartOnTooShortListShouldThrowException() {
        List<PricePoint> prices = List.of(
                new PricePoint("00:00", 1.0),
                new PricePoint("01:00", 0.8)
        );

        assertThrows(IllegalArgumentException.class,
                () -> PriceUtils.findBestWindowStart(prices, 3));
    }
}
