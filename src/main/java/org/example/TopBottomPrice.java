package org.example;

import java.math.BigDecimal;

class TopBottomPrice {
    private final PriceMapper prices;
    private final PriceDates date1 = new PriceDates();
    private final PriceDates date2 = new PriceDates();

    public TopBottomPrice(PriceMapper prices) {
        this.prices = prices;
    }

    private HighLowValues getTopBottomPrice() {
        Price[] allPrices = prices.getAllPrices();
        BigDecimal[] priceArray = new BigDecimal[allPrices.length];
        for (int i = 0; i < allPrices.length; i++)
            priceArray[i] = allPrices[i].getSekPerKWh();

        BigDecimal lowestVal = MinMaxValues.getMinValue(priceArray);
        BigDecimal highestVal = MinMaxValues.getMaxValue(priceArray);
        return new HighLowValues(lowestVal, highestVal);
    }

    public void printTopBottomPrice() {
        Price[] allPrices = prices.getAllPrices();
        HighLowValues topBottomPrice = getTopBottomPrice();

        date1.setDates(topBottomPrice.lowestVal, allPrices);
        date2.setDates(topBottomPrice.highestVal, allPrices);

        System.out.println("Lowest Price: " + topBottomPrice.lowestVal + " SEK");
        date1.printDates();
        System.out.println("\nHighest Price: " + topBottomPrice.highestVal + " SEK");
        date2.printDates();
    }

    private record HighLowValues(BigDecimal lowestVal, BigDecimal highestVal) {}
}

class MinMaxValues {

     static BigDecimal getMinValue(BigDecimal[] priceArray) {
        BigDecimal minValue = priceArray[0];
        for (int i = 1; i < priceArray.length; i++) {
            if(priceArray[i].compareTo(minValue) < 0) {
                minValue = priceArray[i];
            }
        }
        return minValue;
    }

    static BigDecimal getMaxValue(BigDecimal[] priceArray) {
        BigDecimal maxValue = priceArray[0];
        for (int i = 1; i < priceArray.length; i++) {
            if(priceArray[i].compareTo(maxValue) > 0) {
                maxValue = priceArray[i];
            }
        }
        return maxValue;
    }
}
