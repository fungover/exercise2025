package ExerciseOne;

import java.util.List;

public class RemoveHours {

    private final GetDateAndTime getDateAndTime = new GetDateAndTime();

    public List<Pricing> removePastTime(List<Pricing> priceList) {
        if(priceList == null) return List.of();

        int time = getDateAndTime.getHour();

        int hoursToRemove = Math.min(Math.max(0, time), priceList.size());
        priceList.subList(0, hoursToRemove).clear();

        return priceList;
    }
}
