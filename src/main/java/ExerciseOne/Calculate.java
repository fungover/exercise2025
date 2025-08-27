package ExerciseOne;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Calculate {

    public void calculateMean(List<Pricing> priceList){
        int size = priceList.size();
        double sum = priceList.stream()
                .mapToDouble(Pricing::SEK_per_kWh)
                .sum();

        BigDecimal mean = new BigDecimal(sum/size);

        System.out.printf("Mean price coming %,d hours : %.4f SEK per kWh %n",size, mean);
    }

    public void calculateMin(List<Pricing> priceList){

        double minValue = priceList.stream()
                .mapToDouble(Pricing::SEK_per_kWh)
                .min().orElse(0);

        List<Pricing> minValueObject = priceList.stream()
                        .filter(elem -> elem.SEK_per_kWh() == minValue)
                                .toList();

        System.out.println("Min price coming "+minValue);
        System.out.println("Min price:"+ minValueObject.getFirst().time_start());
    }

    public void calculateMax(List<Pricing> priceList){

        double maxValue = priceList.stream()
                .mapToDouble(Pricing::SEK_per_kWh)
                .max().orElse(0);

        List<Pricing> maxValueObject = priceList.stream()
                .filter(elem -> elem.SEK_per_kWh() == maxValue)
                .toList();

        System.out.println("Max price coming "+maxValue);
        System.out.println("Max price:"+maxValueObject.getFirst().time_start());
    }

    public void calculateCheapest(List<Pricing> priceList, int hours){

        List<Cost> listOfCost = new ArrayList<>();

        for(int i = 0; i <= priceList.size()-hours; i++){
            double sum = 0;
            String start = "";
            String end = "";
            for(int j = i; j < i+ hours; j++){
                sum += priceList.get(j).SEK_per_kWh();
                 start = priceList.get(j).time_start();
                 end = priceList.get(j).time_end();
            }
            listOfCost.add(new Cost(sum, start, end));
        }

        displayCheapest(listOfCost);
    }

    public void displayCheapest(List<Cost> prices){
        double cheapestValue = prices.stream()
                .mapToDouble(Cost::cheapest)
                .min().orElse(0);

        List<Cost> cheapestObject = prices.stream()
                .filter(elem -> elem.cheapest() ==  cheapestValue)
                .toList();

        System.out.println("Billigaste tiden att ladda bilen är mellan: "+cheapestObject.getFirst().timeStart()+" och "+cheapestObject.getFirst().timeEnd());
    }

}
//[Pricing[SEK_per_kWh=1.14889, EUR_per_kWh=0.10315, EXR=11.138022, time_start=2025-08-26T00:00:00+02:00, time_end=2025-08-26T01:00:00+02:00],