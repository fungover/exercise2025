package org.example;

import java.math.BigDecimal;

class TopOrBottomPrice {
    private String dateStart;
    private String dateEnd;
    private String timeStart;
    private String timeEnd;
    private final Prices prices = new Prices();

    private void setDates(BigDecimal val, Properties[] priceArray) {
        for (var price : priceArray) {
            if (val.compareTo(price.getSekPerKWh()) == 0) {
                dateStart = price.getTimeStart().substring(0, 10);
                dateEnd = price.getTimeEnd().substring(0, 10);
                timeStart = price.getTimeStart().substring(11, 16);
                timeEnd = price.getTimeEnd().substring(11, 16);
                break;
            }
        }
        System.out.println("Starts: " + dateStart + " " + timeStart);
        System.out.println("Ends: " + dateEnd + " " + timeEnd);
    }

    private BigDecimal getMinValue(BigDecimal[] priceArray) {
        BigDecimal minValue = priceArray[0];
        for (int i = 1; i < priceArray.length; i++) {
            if(priceArray[i].compareTo(minValue) < 0) {
                minValue = priceArray[i];
            }
        }
        return minValue;
    }

    private BigDecimal getMaxValue(BigDecimal[] priceArray) {
        BigDecimal maxValue = priceArray[0];
        for (int i = 1; i < priceArray.length; i++) {
            if(priceArray[i].compareTo(maxValue) > 0) {
                maxValue = priceArray[i];
            }
        }
        return maxValue;
    }

    public void getTopBottomPrice() {
        Properties[] obj = prices.getPrices();
        BigDecimal[] priceArray = new BigDecimal[obj.length];

        for (int i = 0; i < obj.length; i++) {
            priceArray[i] = obj[i].getSekPerKWh();
        }
        BigDecimal lowestVal = getMinValue(priceArray);
        BigDecimal highestVal = getMaxValue(priceArray);

        System.out.println("Lowest Price: " + lowestVal + " SEK");
        setDates(lowestVal, obj);
        System.out.println("\nHighest Price: " + highestVal + " SEK");
        setDates(highestVal, obj);
    }
}
