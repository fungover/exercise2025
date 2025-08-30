package org.example;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.MonthDay;
import java.time.Year;

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
    private final DecimalFormat dFormat = new DecimalFormat("00");
    private final LocalDate presentDate = LocalDate.now();
    private final String year = String.valueOf(Year.now().getValue());
    private final String month = dFormat.format(Month.from(presentDate).getValue());
    private final String today = dFormat.format(MonthDay.now().getDayOfMonth());
    private final String tomorrow = dFormat.format(MonthDay.of(
                    Integer.parseInt(month), Integer.parseInt(today) + 1)
            .getDayOfMonth());

    public String getYear() {
        return year;
    }
    public String getMonth() {
        return month;
    }
    public String getToday() {
        return today;
    }
    public String getTomorrow() {
        return tomorrow;
    }
}
