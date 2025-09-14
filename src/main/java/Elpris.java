import java.time.LocalDate;
import java.time.format.DateTimeFormatter;     //Denna plus ovan hjälper till att autmatiskt bygga rätt datum.
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


class HourData {
    String time_start;
    String time_end;
    double SEK_per_kWh;
}


public class Elpris {
    public static void main(String[] args) {
        String zone = "SE3";

        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);

        String todayApi = buildApiUrl(today, zone);
        String tomorrowApi = buildApiUrl(tomorrow, zone);

        String todayJson = fetchJson(todayApi);
        List<HourData> todayHours = jsonHourDataList(todayJson);

        String tomorrowJson = fetchJson(tomorrowApi);
        List<HourData> tomorrowHours = jsonHourDataList(tomorrowJson);

        System.out.println("=== Idag ===");
        printMeanPrice(todayHours);
        printCheapestAndMostExpensive(todayHours);

        if (tomorrowHours != null && !tomorrowHours.isEmpty()){
            System.out.println("\n=== Imorgon ===");
            printMeanPrice(tomorrowHours);
            printCheapestAndMostExpensive(tomorrowHours);
        } else {
            System.out.println("\nMorgondagen är ännu inte publicerad.");
        }

    
    }

    public static String fetchJson(String urlString) {
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


    public static void printMeanPrice(List<HourData> hours) {
        double sum = 0;

        for (HourData h : hours) {
            sum += h.SEK_per_kWh;
        }

        double mean = sum / hours.size();
        System.out.println("Medelpris för perioden: " + mean + " kr/kWh");
    }



    public static void printCheapestAndMostExpensive(List<HourData> hours) {
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

        System.out.println("Billigaste timmen: " + cheapest.time_start +
                " - " + cheapest.time_end + " : " +
                cheapest.SEK_per_kWh + " kr/kWh");

        System.out.println("Dyraste timmen: " + mostExpensive.time_start +
                           " - " + mostExpensive.time_end + " : " +
                mostExpensive.SEK_per_kWh + " kr/kWh");
    }


    public static String buildApiUrl (LocalDate date, String zone) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM-dd");
        String formattedDate = date.format(formatter);
        return "https://www.elprisetjustnu.se/api/v1/prices/" + formattedDate + "_" + zone + ".json";
    }

}
