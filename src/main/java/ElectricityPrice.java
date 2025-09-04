 /*
     * Record for storing electricity price data
     * Contains price per kWh, hour and time interval as a string
     */
    public record ElectricityPrice (double sekPerKwh, int hour, String tid) {

        public ElectricityPrice {
            if (sekPerKwh < 0) {
                throw new IllegalArgumentException("Pris kan inte vara negativt");
            }
            if (hour < 0 || hour > 23) {
                throw new IllegalArgumentException("Timme måste vara mellan 0 och 23");
            }
            if (tid == null || tid.isBlank()) {
                throw new IllegalArgumentException("Tid-sträng kan inte vara null eller tom");
            }
        }

        @Override
        public String toString() {
            return String.format("Timme %02d: %.4f SEK/kWh (%s)", hour, sekPerKwh, tid);
        }
    }
