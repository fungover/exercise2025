package org.example;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class App {
    record ElectricityPrice(double SEK_per_kWh, double EUR_per_kWh, String EXR, String time_start, String time_end) {}

    public static void main(String[] args) {

        System.out.println("Welcome to the electricity calculator");
        ArrayList<ElectricityPrice> electricityPrices = getElectricityPrices(LocalDate.now(), LocalTime.now());
        showMenu(electricityPrices);
        //handleElectricityPrices(date, time);
    }

    private static void showMenu(ArrayList<ElectricityPrice> electricityPrices) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("What do you want to know?");
        System.out.println("1. What is the mean price of the current 24 h period?");
        System.out.println("2. When is the cheapest hour?");
        System.out.println("3. When is the most expensive hour?");
        System.out.println("4. When is the best time to charge an electric car for 2, 4 or 8 hours?");
        System.out.println("Press 0 to exit");
        int choice = scanner.nextInt();
        switch (choice) {
            case 0: break;
            case 1: calculateMean(electricityPrices);
                    break;
            case 2: findCheapestHour(electricityPrices);
                    break;
            case 3: findMostExpensiveHour(electricityPrices);
                    break;
            case 4: findCheapestPeriod(2, electricityPrices);
                    findCheapestPeriod(4, electricityPrices);
                    findCheapestPeriod(8, electricityPrices);
                    break;


        }
    }

    public static ArrayList<ElectricityPrice> getElectricityPrices(LocalDate date, LocalTime time){
        Scanner scanner = new Scanner(System.in);
        ArrayList<ElectricityPrice> electricityPrices;
        String timeString = time.toString();
        String hourString = timeString.substring(0,2);
        int hour = Integer.parseInt(hourString);
        System.out.println("What zone do you want the information for?");
        System.out.println("Type SE1, SE2, SE3 or SE4");
        String zone = scanner.nextLine();
        electricityPrices= getElectricityPrices(date, hour, zone);
        return electricityPrices;
       /* calculateMean(electricityPrices);
        findCheapestHour(electricityPrices);
        findMostExpensiveHour(electricityPrices);
        findCheapestPeriod(2, electricityPrices);
        findCheapestPeriod(4, electricityPrices);
        findCheapestPeriod(8, electricityPrices);*/
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
        String URL = "https://www.elprisetjustnu.se/api/v1/prices" + "/"+date+"_"+zone+".json";
        try {
            HttpResponse<String> response;
            try (HttpClient client = HttpClient.newHttpClient()) {
                HttpRequest request = HttpRequest.newBuilder().uri(URI.create(URL)).build();
                response = client.send(request, HttpResponse.BodyHandlers.ofString());
            }
            String json = response.body();
            ObjectMapper objectMapper = new ObjectMapper();
            ArrayList <ElectricityPrice>electricity = objectMapper.readValue(json, new TypeReference<ArrayList<ElectricityPrice>>() {
            });
            return electricity;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static void findCheapestPeriod(int k, ArrayList<ElectricityPrice> electricityPrices) {
        double windowSum = 0;
        int startHour = 0;
        int endHour = 0;
        for(int i=0; i<k; i++){
            windowSum += electricityPrices.get(i).SEK_per_kWh;
        }
        double minSum = Integer.MAX_VALUE;
        LocalDate date = null;
        for(int i=k; i< electricityPrices.size(); i++){
            windowSum += electricityPrices.get(i).SEK_per_kWh-electricityPrices.get(i-k).SEK_per_kWh;
            if(windowSum < minSum){
                minSum = windowSum;
                String startTime = electricityPrices.get(i-k).time_start;
                String hour= startTime.substring(11,13);
                startHour = Integer.parseInt(hour);
                endHour = startHour+k;
                date = LocalDate.parse(electricityPrices.get(i).time_start.substring(0,10));
            }
        }
        if(date.isAfter(LocalDate.now())){
            System.out.println("The cheapest time to charge an electric car for "+k+" hours is between "+startHour+" and "+endHour+" tomorrow.");
        }else{
            System.out.println("The cheapest time to charge an electric car for "+k+" hours is between "+startHour+" and "+endHour+" today.");
        }
        showMenu(electricityPrices);
    }

    private static void findMostExpensiveHour(ArrayList<ElectricityPrice> electricityPrices) {
        double mostExpensive = Integer.MIN_VALUE;
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
        showMenu(electricityPrices);
    }

    private static void findCheapestHour(ArrayList<ElectricityPrice> electricityPrices) {
        double cheapest = Integer.MAX_VALUE;
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
        showMenu(electricityPrices);
    }

    private static void calculateMean(ArrayList<ElectricityPrice> electricityPrices) {
        double totalPrice = 0;
        for(ElectricityPrice electricityPrice : electricityPrices){
            totalPrice += electricityPrice.SEK_per_kWh;
        }
        double mean = totalPrice / electricityPrices.size();
        System.out.println("The mean price for the current 24h period is " + mean + " SEK per kWh");
        showMenu(electricityPrices);
    }
}
