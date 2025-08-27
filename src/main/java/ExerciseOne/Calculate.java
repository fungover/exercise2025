package ExerciseOne;

import java.math.BigDecimal;
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
}
//[Pricing[SEK_per_kWh=1.14889, EUR_per_kWh=0.10315, EXR=11.138022, time_start=2025-08-26T00:00:00+02:00, time_end=2025-08-26T01:00:00+02:00],