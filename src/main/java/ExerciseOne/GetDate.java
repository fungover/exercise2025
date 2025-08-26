package ExerciseOne;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class GetDate {

    public String geToday(int area) {

        String today = formatDate(LocalDate.now());
        return today+"_SE"+area;
    }

    public int getTime(){
        LocalTime time = LocalTime.now();
        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("HH");
        String  formatedTime = time.format(dtf2);

        return Integer.parseInt(formatedTime);
    }

    public String getTomorrow(int area) {
        String tomorrow = formatDate(LocalDate.now().plusDays(1));
        return tomorrow+"_SE"+area;
    }

    public String formatDate(LocalDate date){
        return date.format(DateTimeFormatter.ofPattern("yyyy/MM-dd"));
    }

}
