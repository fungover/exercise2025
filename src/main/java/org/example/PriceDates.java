package org.example;

import java.math.BigDecimal;
import java.time.LocalDate;

class PriceDates {
    private String dateStart;
    private String dateEnd;
    private String timeStart;
    private String timeEnd;

    public void setDates(BigDecimal val, Price[] priceArray) {
        for (var price : priceArray) {
            if (val.compareTo(price.getSekPerKWh()) == 0) {
                dateStart = price.getTimeStart().substring(0, 10);
                dateEnd = price.getTimeEnd().substring(0, 10);
                timeStart = price.getTimeStart().substring(11, 16);
                timeEnd = price.getTimeEnd().substring(11, 16);
                break;
            }
        }
    }

    public void printDates() {
        System.out.println("Starts: " + dateStart + " " + timeStart);
        System.out.println("Ends: " + dateEnd + " " + timeEnd);
    }
}

class FetchDates {
    private final LocalDate presentDate = LocalDate.now();

    public String getYear() {
        return String.valueOf(presentDate.getYear());
    }
    public String getMonth() {
        return String.format("%02d", presentDate.getMonthValue());
    }
    public String getToday() {
        return String.format("%02d", presentDate.getDayOfMonth());
    }
    public String getTomorrowDay() {
        LocalDate tomorrowDay = presentDate.plusDays(1);
        return String.format("%02d", tomorrowDay.getDayOfMonth());
    }

    public String getTomorrowMonth() {
        LocalDate tomorrowMonth = presentDate.plusMonths(1);
        return String.format("%02d", tomorrowMonth.getMonthValue());

    }
}
