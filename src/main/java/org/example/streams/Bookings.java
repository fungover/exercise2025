package org.example.streams;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Bookings {
    public static void main(String[] args) {
        // Initialize an ArrayList with HotelBooking objects
        List<HotelBooking> bookings = getHotelBookings();

        // Print the bookings
        bookings.forEach(System.out::println);

        // Print number of bookings in total

        // Print total number of bookings for single rooms

        // How many nights has Anderson booked?

        // List all guest names

        // All bookings longer than 3 nights

        // Number of bookings for each room type

        // Find the quest with the longest stay

        // Sort bookings by arrival date

        // Do we have any bookings in November? true/false

        // Return any suit booking if available

        // Find the max number of bookings the same day... so we have enough rooms... b.getStartDate().datesUntil(b.getEndDate())

    }

    private static List<HotelBooking> getHotelBookings() {
        List<HotelBooking> bookings = new ArrayList<>();

        // Add some bookings to the list
        bookings.add(new HotelBooking(new Guest("Alice", "Smith"), "Single", LocalDate.of(2025, 10, 1), LocalDate.of(2025, 10, 4), BigDecimal.valueOf(100.0)));
        bookings.add(new HotelBooking(new Guest("Bob", "Johnson"), "Double", LocalDate.of(2025, 10, 5), LocalDate.of(2025, 10, 7), BigDecimal.valueOf(150.0)));
        bookings.add(new HotelBooking(new Guest("Carol", "White"), "Suite", LocalDate.of(2025, 10, 8), LocalDate.of(2025, 10, 13), BigDecimal.valueOf(300.0)));
        bookings.add(new HotelBooking(new Guest("David", "Brown"), "Single", LocalDate.of(2025, 10, 14), LocalDate.of(2025, 10, 16), BigDecimal.valueOf(100.0)));
        bookings.add(new HotelBooking(new Guest("Eve", "Davis"), "Double", LocalDate.of(2025, 10, 17), LocalDate.of(2025, 10, 19), BigDecimal.valueOf(150.0)));
        bookings.add(new HotelBooking(new Guest("Frank", "Miller"), "Suite", LocalDate.of(2025, 10, 20), LocalDate.of(2025, 10, 25), BigDecimal.valueOf(300.0)));
        bookings.add(new HotelBooking(new Guest("Grace", "Wilson"), "Single", LocalDate.of(2025, 10, 26), LocalDate.of(2025, 10, 28), BigDecimal.valueOf(100.0)));
        bookings.add(new HotelBooking(new Guest("Hank", "Moore"), "Double", LocalDate.of(2025, 10, 29), LocalDate.of(2025, 10, 31), BigDecimal.valueOf(150.0)));
        bookings.add(new HotelBooking(new Guest("Ivy", "Taylor"), "Suite", LocalDate.of(2025, 11, 1), LocalDate.of(2025, 11, 6), BigDecimal.valueOf(300.0)));
        bookings.add(new HotelBooking(new Guest("Jack", "Anderson"), "Single", LocalDate.of(2025, 11, 7), LocalDate.of(2025, 11, 9), BigDecimal.valueOf(100.0)));
        return bookings;
    }
}