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
                dateStart = price.getStartDate();
                dateEnd = price.getEndDate();
                timeStart = price.getStartHour();
                timeEnd = price.getEndHour();
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
        LocalDate tomorrow = presentDate.plusDays(1);
        return String.format("%02d", tomorrow.getMonthValue());

    }
}
