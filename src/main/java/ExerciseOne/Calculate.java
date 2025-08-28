package ExerciseOne;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Calculate {

    public void calculateMean(List<Pricing> priceList){
        int size = priceList.size();

        if(priceList.isEmpty()){
            System.out.println("Inga tillgängliga priser");
            return;
        }
        double sum = priceList.stream()
                .mapToDouble(Pricing::SEK_per_kWh)
                .sum();

        double mean = sum/size;

        System.out.printf("Medelpriset för aktuellt dygn är : %.2f SEK per kWh %n", mean);
    }

    public void calculateMin(List<Pricing> priceList){

        if(priceList.isEmpty()){
            System.out.println("Inga tillgängliga priser");
            return;
        }

        double minValue = priceList.stream()
                .mapToDouble(Pricing::SEK_per_kWh)
                .min().orElse(0);

        List<Pricing> minValueObject = priceList.stream()
                        .filter(elem -> elem.SEK_per_kWh() == minValue)
                                .toList();

        OffsetDateTime time = OffsetDateTime.parse(minValueObject.getFirst().time_start());
        GetDateAndTime gd = new GetDateAndTime();

        System.out.println("Lägsta kilowattpriset kommande "+priceList.size()+" timmar är "
                +gd.formatDateAndTime(time)+": "+minValue+" SEK per kWh");
    }

    public void calculateMax(List<Pricing> priceList){

        if(priceList.isEmpty()){
            System.out.println("Inga tillgängliga priser");
            return;
        }

        double maxValue = priceList.stream()
                .mapToDouble(Pricing::SEK_per_kWh)
                .max().orElse(0);

        List<Pricing> maxValueObject = priceList.stream()
                .filter(elem -> elem.SEK_per_kWh() == maxValue)
                .toList();

        OffsetDateTime time = OffsetDateTime.parse(maxValueObject.getFirst().time_start());
        GetDateAndTime gd = new GetDateAndTime();

        System.out.println("Högsta kilowattpriset kommande "+priceList.size()+" timmar är "
                +gd.formatDateAndTime(time)+": "+maxValue+" SEK per kWh");
    }

    public void calculateCheapest(List<Pricing> priceList, int hours){

        if(priceList.isEmpty()){
            System.out.println("Inga tillgängliga priser");
            return;
        }

        List<Cost> listOfCost = new ArrayList<>();

        for(int i = 0; i <= priceList.size()-hours; i++){
            double sum = 0;
            String start = priceList.get(i).time_start();
            String end = priceList.get(i + hours -1).time_end();

            for(int j = i; j < i+ hours; j++){
                sum += priceList.get(j).SEK_per_kWh();
            }
            listOfCost.add(new Cost(sum, start, end));
        }

        displayCheapest(listOfCost, hours);
        listOfCost.clear();
    }

    public void displayCheapest(List<Cost> prices, int hours){
        double cheapestValue = prices.stream()
                .mapToDouble(Cost::cheapest)
                .min().orElse(0);

        List<Cost> cheapestObject = prices.stream()
                .filter(elem -> elem.cheapest() ==  cheapestValue)
                .toList();

        OffsetDateTime start = OffsetDateTime.parse(cheapestObject.getFirst().timeStart());
        GetDateAndTime gd = new GetDateAndTime();

        System.out.println("Billigaste tiden att ladda bilen "+hours +" i sträck är från: "+gd.formatDateAndTime(start));
    }

    public void costOfConsumption(List<Consumption> consumption, List<Pricing> priceList){

        double sum = 0;
        double consumedElectricity = 0;
        
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        for (Consumption value : consumption) {
            String time = value.time();
            LocalDateTime formatedTime = LocalDateTime.parse(time, dtf);

            for (Pricing pricing : priceList) {

                OffsetDateTime priceTime = OffsetDateTime.parse(pricing.time_start());

                if (priceTime.toLocalDateTime().isEqual(formatedTime)) {
                    double price = pricing.SEK_per_kWh();
                    double consumed = value.kWh();
                    sum += price * consumed;
                    consumedElectricity += consumed;

                }
            }
        }
        System.out.printf("Förbrukning på %.2f kWh innebär en kostnad på %.2f SEK", consumedElectricity, sum);

    }

}
//[Pricing[SEK_per_kWh=1.14889, EUR_per_kWh=0.10315, EXR=11.138022, time_start=2025-08-26T00:00:00+02:00, time_end=2025-08-26T01:00:00+02:00],