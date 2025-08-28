package ExerciseOne;

import java.util.List;

public class RemoveHours {

    public List<Pricing> removePastTime(List<Pricing> priceList) {

        GetDateAndTime getDateAndTime = new GetDateAndTime();
        int time = getDateAndTime.getHour();

        for(int i = 0; i < time; i++){
            priceList.removeFirst();
        }
    return priceList;
    }
}
