package ExerciseOne;

import java.util.List;

public class RemoveHours {

    public List<Pricing> removePastTime(List<Pricing> priceList) {

        GetDateAndTime getDateAndTime = new GetDateAndTime();
        int time = getDateAndTime.getHour();

        int hoursToRemove = Math.min(Math.max(0, time), priceList.size());
        priceList.subList(0, hoursToRemove).clear();

        return priceList;
    }
}
