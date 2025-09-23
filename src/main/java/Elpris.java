import java.time.LocalDate;
import java.time.format.DateTimeFormatter;     //Denna plus ovan hjälper till att automatiskt bygga rätt datum.
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.OffsetDateTime;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;



class HourData {
    String time_start;
    String time_end;
    double SEK_per_kWh;
}


public class Elpris {
    public static void main(String[] args) {
        String zone = "SE3";         //Kan ändras vid argument. (F:K högerklicka "modify run config och välj område)

        if (args.length > 0) {
            zone = args[0].toUpperCase();
        }

        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);

        String todayApi = buildApiUrl(today, zone);
        String tomorrowApi = buildApiUrl(tomorrow, zone);

        String todayJson = fetchJson(todayApi);
        List<HourData> todayHours = jsonHourDataList(todayJson);

        String tomorrowJson = fetchJson(tomorrowApi);
        List<HourData> tomorrowHours = jsonHourDataList(tomorrowJson);

        System.out.println("=== Idag (" + zone + ") ===");
        printMeanPrice(todayHours);
        printCheapestAndMostExpensive(todayHours);

        BestChargingTime(todayHours, 2);
        BestChargingTime(todayHours, 4);
        BestChargingTime(todayHours, 8);


        if (tomorrowHours != null && !tomorrowHours.isEmpty()){
            System.out.println("\n=== Imorgon (" + zone + ") ===");
            printMeanPrice(tomorrowHours);
            printCheapestAndMostExpensive(tomorrowHours);

            BestChargingTime(tomorrowHours, 2);
            BestChargingTime(tomorrowHours, 4);
            BestChargingTime(tomorrowHours, 8);

        } else {
            System.out.println("\nMorgondagen är ännu inte publicerad.");
        }

    }

    public static String fetchJson(String urlString) {      // // Hämtas JSON från API via HTTP GET
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream())
            );

            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }

            in.close();
            conn.disconnect();

            return content.toString();
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<HourData> jsonHourDataList(String json) {
        Gson gson = new Gson();
        Type listType = new TypeToken<List<HourData>>() {
        }.getType();
        return gson.fromJson(json, listType);
    }


    public static void printMeanPrice(List<HourData> hours) {     //skriver ut medelpriset
        double sum = 0;

        for (HourData h : hours) {
            sum += h.SEK_per_kWh;
        }

        double mean = sum / hours.size();
        System.out.println("Medelpris för perioden: " + mean + " kr/kWh");
    }



    public static void printCheapestAndMostExpensive(List<HourData> hours) { //Dyraste och billigaste timmen
        if (hours.isEmpty()) {
            System.out.println("Ingen data tillgänglig.");
            return;
        }

        HourData cheapest = hours.get(0);
        HourData mostExpensive = hours.get(0);

        for (HourData h : hours) {
            if (h.SEK_per_kWh < cheapest.SEK_per_kWh) {
                cheapest = h;
            }
            if (h.SEK_per_kWh > mostExpensive.SEK_per_kWh) {
                mostExpensive = h;
            }
        }

        System.out.println("Billigaste timmen: " + formatTime(cheapest.time_start) +
                " - " + formatTime(cheapest.time_end) + " : " +
                String.format("%.3f", cheapest.SEK_per_kWh) + " kr/kWh");

        System.out.println("Dyraste timmen: " + formatTime(mostExpensive.time_start) +
                           " - " + formatTime(mostExpensive.time_end) + " : " +
                           String.format("%.3f", mostExpensive.SEK_per_kWh) + " kr/kWh");
    }


    public static String buildApiUrl (LocalDate date, String zone) { //Bygger API-url utifrån datum och zon.
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM-dd");
        String formattedDate = date.format(formatter);
        return "https://www.elprisetjustnu.se/api/v1/prices/" + formattedDate + "_" + zone + ".json";
    }



    public static void BestChargingTime(List<HourData> hours, int duration) { //Letar efter de bästa timmarna att ladda.
        if (hours.size() < duration) {
            System.out.println("Det finns förnärvarande inte tillräckligt många timmar i datan.");
            return;

        }

        double bestAvg = Double.MAX_VALUE;
        int bestStartIndex = 0;

        for (int i = 0; i <= hours.size() - duration; i++) {   //Testar möjliga startpunkter.
            double sum = 0;
            for (int j = 0; j <duration; j++) {
                sum += hours.get(i + j).SEK_per_kWh;
            }

            double avg = sum / duration;

            if (avg < bestAvg) {
                bestAvg = avg;
                bestStartIndex = i;
            }
        }

        HourData start = hours.get(bestStartIndex);
        HourData end = hours.get(bestStartIndex + duration -1);

        System.out.println("Det här är bästa tiden för laddning (" + duration + "h): " +
                formatTime(start.time_start) + " - " + formatTime(end.time_end) +
                " med snittpris " + String.format("%.3f", bestAvg) + " kr/kWh");
    }


    public static String formatTime(String isoString) {    //Formatering till förståelig tid och datum.
      LocalDateTime time = OffsetDateTime.parse(isoString, DateTimeFormatter.ISO_OFFSET_DATE_TIME)
                                         .toLocalDateTime();
      return time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }






}
