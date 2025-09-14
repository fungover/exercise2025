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
        String api = "https://www.elprisetjustnu.se/api/v1/prices/2025/09-13_SE3.json";
        String json = fetchJson(api);
        System.out.println(json);
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
}
