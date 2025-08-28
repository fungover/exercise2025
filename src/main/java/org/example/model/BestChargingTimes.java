package org.example.model;

public class BestChargingTimes {
    public record BestWindow(int startIndex, int length, double totalSek) {
        public BestWindow {
            if (startIndex < 0) throw new IllegalArgumentException("startIndex must be >= 0");
            if (length <= 0) throw new IllegalArgumentException("length must be > 0");
            if (totalSek < 0) throw new IllegalArgumentException("totalSek must be >= 0");
            if (startIndex > Integer.MAX_VALUE - length) throw new
                    IllegalArgumentException("startIndex + lenght overflows int range");
        }
    }


}
