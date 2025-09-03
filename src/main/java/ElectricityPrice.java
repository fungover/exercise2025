 /**
     * Record för att lagra elpris-data
     * Innehåller pris per kWh, timme och tidsintervall som sträng
     */
    public record ElectricityPrice (double sekPerKwh, int timme, String tid) {

        /**
         * Konstruktor med validering
         */
        public ElectricityPrice {
            if (sekPerKwh < 0) {
                throw new IllegalArgumentException("Pris kan inte vara negativt");
            }
            if (timme < 0 || timme > 23) {
                throw new IllegalArgumentException("Timme måste vara mellan 0 och 23");
            }
            if (tid == null || tid.isBlank()) {
                throw new IllegalArgumentException("Tid-sträng kan inte vara null eller tom");
            }
        }

        @Override
        public String toString() {
            return String.format("Timme %02d: %.4f SEK/kWh (%s)", timme, sekPerKwh, tid);
        }

        /*
         * Jämför två ElPris-objekt baserat på pris (lägst först)
         public static int compareByPrice(ElectricityPrice e1, ElectricityPrice e2) {
            return Double.compare(e1.sekPerKwh, e2.sekPerKwh);
        }*/

        /*
         * Jämför två ElPris-objekt baserat på timme (tidigast först)
         public static int compareByHour(ElectricityPrice e1, ElectricityPrice e2) {
            return Integer.compare(e1.timme, e2.timme);
        }*/
    }
