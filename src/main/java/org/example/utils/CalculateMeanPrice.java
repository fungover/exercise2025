package org.example.utils;

public class CalculateMeanPrice {

    public static double meanSEK(String json) {
        String target = "\"SEK_per_kWh\":"; // The key to search for is in the JSON string
        int index = 0; // Current search position in the string
        int count = 0; // number of values found
        double sum = 0.0; // Sum of all values found

        // Search for the target string, from position index. index.Of returns -1 if the target is not found.
        while ((index = json.indexOf(target, index)) != -1) {

            index += target.length(); // Move the index to the end of the target string (SEK_per_kWh")

            // Skip any whitespace, tabs or newlines after the target string to ensure we start reading the number
            while (index < json.length() && Character.isWhitespace(json.charAt(index))) {
                index++;
            }

            StringBuilder num = new StringBuilder(); // StringBuilder to build the number string since strings in java are immutable.

            //Read the number until we hit a character that is not a digit or a dot. (0-9 or .)
            while (index < json.length() && (Character.isDigit(json.charAt(index)) || json.charAt(index) == '.')) {
                num.append(json.charAt(index));
                index++;
            }

            // If we found a number, we convert it to a double and add it to the sum.
            if (num.length() > 0) {
                double value = Double.parseDouble(num.toString());
                sum += value;
                count++;
            }
        }

        // If no values are found we throw an exception
        if (count == 0) {
            throw new IllegalArgumentException("No SEK_per_kWh values found in the JSON string.");
        }
        return sum / count;
}
}



// ASK TEACHER ABOUT: ÄR DET OK ATT ANVÄNDA JSON BILIOTEKET FÖR ATT PARSA JSON? ELLER SKA VI GÖRA DET MANUELLT?