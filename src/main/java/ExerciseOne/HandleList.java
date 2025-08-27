package ExerciseOne;

import java.util.ArrayList;
import java.util.List;

public class HandleList {

  private final List<Pricing> listOfPrices =  new ArrayList<>();

    public void addList(List<Pricing> priceList) {
        listOfPrices.addAll(priceList);
    }

    public List<Pricing> getListOfPrices() {
        return listOfPrices;
    }

    public void removePastTime() {

        GetDateAndTime getDateAndTime = new GetDateAndTime();
        int time = getDateAndTime.getHour();

            for(int i = 0; i < time; i++){
                listOfPrices.removeFirst();
            }
    }
 }
