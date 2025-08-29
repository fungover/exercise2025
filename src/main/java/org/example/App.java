package org.example;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.DeserializationFeature;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Set;
import java.util.Objects;
import java.util.Scanner;


public class App {
   @JsonIgnoreProperties(ignoreUnknown = true)
    record ElectricityPrice(double SEK_per_kWh, double EUR_per_kWh, String EXR, String time_start, String time_end) {}
    public static void main(String[] args) {
        System.out.println("Welcome to the electricity calculator");
        ArrayList<ElectricityPrice> electricityPrices = getElectricityPrices(LocalDate.now(), LocalTime.now());
        showMenu(electricityPrices);
    }

   private static void showMenu(ArrayList<ElectricityPrice> electricityPrices) {
       Scanner scanner = new Scanner(System.in);
       while (true) {
           System.out.println("What do you want to know?");
           System.out.println("1. What is the mean price of the current 24 h period?");
           System.out.println("2. When is the cheapest hour?");
           System.out.println("3. When is the most expensive hour?");
           System.out.println("4. When is the best time to charge an electric car for 2 hours?");
           System.out.println("5. When is the best time to charge an electric car for 4 hours?");
           System.out.println("6. When is the best time to charge an electric car for 8 hours?");
           System.out.println("Press 0 to exit");
           System.out.print("> ");
           String input = scanner.nextLine().trim();
           switch (input) {
               case "0": return;
               case "1": calculateMean(electricityPrices); break;
               case "2": findCheapestHour(electricityPrices); break;
               case "3": findMostExpensiveHour(electricityPrices); break;
               case "4": findCheapestPeriod(2, electricityPrices); break;
               case "5": findCheapestPeriod(4, electricityPrices); break;
               case "6": findCheapestPeriod(8, electricityPrices); break;
               default: System.out.println("Invalid choice. Try again.");
           }
       }
   }

    private static ArrayList<ElectricityPrice> getElectricityPrices(LocalDate date, LocalTime time){
        Scanner scanner = new Scanner(System.in);
        ArrayList<ElectricityPrice> electricityPrices;
        int hour = time.getHour();
        System.out.println("What zone do you want the information for?");
        System.out.println("Type SE1, SE2, SE3 or SE4");
        String zone;
        while(true){
            zone = scanner.nextLine().trim().toUpperCase();
            if(Set.of("SE1","SE2","SE3","SE4").contains(zone))break;
            System.out.println("Invalid zone. Type SE1, SE2, SE3 or SE4");
        }
        electricityPrices= getElectricityPrices(date, hour, zone);
        return electricityPrices;
    }

    private static ArrayList<ElectricityPrice> getElectricityPrices(LocalDate date, int hour, String zone) {
        ArrayList<ElectricityPrice> electricityPrices;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM-dd");
        String formattedDate = date.format(formatter);
        if(hour < 13){
            electricityPrices = jsonDownloadAndConvert(formattedDate, zone);
        }
        else{
            LocalDate nextDay = LocalDate.now().plusDays(1);
            String formattedNextDay = nextDay.format(formatter);
            electricityPrices = jsonDownloadAndConvert(formattedDate, zone);
            ArrayList<ElectricityPrice> electricityPricesTomorrow = jsonDownloadAndConvert(formattedNextDay, zone);
            electricityPrices.addAll(electricityPricesTomorrow);
            electricityPrices.subList(0, hour).clear();
            if (electricityPrices.size() > 24) {
                electricityPrices.subList(24, electricityPrices.size()).clear();
            }
        }
        return electricityPrices;
    }

    private static ArrayList<ElectricityPrice> jsonDownloadAndConvert(String date, String zone) {
        String URL = "https://www.elprisetjustnu.se/api/v1/prices" + "/" + date + "_" + zone + ".json";
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(URL)).build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                throw new IOException("Unexpected HTTP " + response.statusCode() + " from " + URL);
            }
            String json = response.body();
            ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return new ArrayList<>(objectMapper.readValue(json, new TypeReference<ArrayList<ElectricityPrice>>() {
            }));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void findCheapestPeriod(int k, ArrayList<ElectricityPrice> electricityPrices) {
            if(electricityPrices.size() < k){
                System.out.println("Not enough data to compute a "+k+" hour window.");
                return;
            }
            double windowSum = 0;
            for (int i = 0; i < k; i++) {
                windowSum += electricityPrices.get(i).SEK_per_kWh;
            }
            double minSum = windowSum;
            int bestStart = 0;
            for(int i = k; i < electricityPrices.size(); i++){
                windowSum += electricityPrices.get(i).SEK_per_kWh - electricityPrices.get(i-k).SEK_per_kWh;
                if(windowSum < minSum){
                    minSum = windowSum;
                    bestStart = i-k+1;
                }
            }
            String startTime = electricityPrices.get(bestStart).time_start;
            String endTime = electricityPrices.get(bestStart+k-1).time_end;
            int startHour = Integer.parseInt(startTime.substring(11, 13));
            int endHour = Integer.parseInt(endTime.substring(11, 13));
            LocalDate date = LocalDate.parse(startTime.substring(0,10));
            if(date.isAfter(LocalDate.now())){
                System.out.println("The cheapest time to charge an electric car for "+k+" hours is between "+startHour+" and "+endHour+" tomorrow.");
            }else{
                System.out.println("The cheapest time to charge an electric car for "+k+" hours is between "+startHour+" and "+endHour+" today.");
            }
    }

    private static void findMostExpensiveHour(ArrayList<ElectricityPrice> electricityPrices) {
            if(electricityPrices.isEmpty()){
                System.out.println("No data available");
                return;
            }
        double mostExpensive = Double.NEGATIVE_INFINITY;
        String time = null;
        LocalDate date = LocalDate.now();
        for(ElectricityPrice electricityPrice : electricityPrices) {
            if(electricityPrice.SEK_per_kWh > mostExpensive){
                mostExpensive = electricityPrice.SEK_per_kWh;
                time = electricityPrice.time_start;
                date = LocalDate.parse(electricityPrice.time_start.substring(0,10));
            }
        }
        String hour= Objects.requireNonNull(time).substring(11,13);
        int startHour = Integer.parseInt(hour);
        int endHour = startHour+1;
        if(date.isAfter(LocalDate.now())){
            System.out.println("The most expensive hour is between "+startHour+"  and "+endHour+" tomorrow. The price is "+mostExpensive+" SEK per kWh.");
        }else{
            System.out.println("The most expensive hour is between "+startHour+"  and "+endHour+" today. The price is "+mostExpensive+" SEK per kWh.");
        }
    }

    private static void findCheapestHour(ArrayList<ElectricityPrice> electricityPrices) {
        if(electricityPrices.isEmpty()){
            System.out.println("No data available.");
            return;
        }
        double cheapest = Double.POSITIVE_INFINITY;
        LocalDate date = LocalDate.now();
        String time = null;
        for(ElectricityPrice electricityPrice : electricityPrices) {
            if(electricityPrice.SEK_per_kWh < cheapest){
                cheapest = electricityPrice.SEK_per_kWh;
                time = electricityPrice.time_start;
                date = LocalDate.parse(electricityPrice.time_start.substring(0,10));
            }
        }
        String hour= Objects.requireNonNull(time).substring(11,13);
        int startHour = Integer.parseInt(hour);
        int endHour = startHour+1;
        if(date.isAfter(LocalDate.now())){
            System.out.println("The cheapest hour is between "+startHour+"  and "+endHour+" tomorrow. The price is "+cheapest+" SEK per kWh.");
        }else{
            System.out.println("The cheapest hour is between "+startHour+"  and "+endHour+" today. The price is "+cheapest+" SEK per kWh.");
        }
    }

    private static void calculateMean(ArrayList<ElectricityPrice> electricityPrices) {
       if(electricityPrices.isEmpty()){
           System.out.println("No data available.");
           return;
       }
        double totalPrice = 0;
        for(ElectricityPrice electricityPrice : electricityPrices){
            totalPrice += electricityPrice.SEK_per_kWh;
        }
        double mean = totalPrice / electricityPrices.size();
        System.out.println("The mean price for the current 24h period is " + mean + " SEK per kWh");
    }
}
