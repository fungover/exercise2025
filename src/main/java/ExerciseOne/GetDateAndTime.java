package ExerciseOne;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public class GetDateAndTime {

    public String getToday(int area) {

        String today = formatDate(LocalDate.now());
        return today+"_SE"+area;
    }

    public String getTomorrow(int area) {
        String tomorrow = formatDate(LocalDate.now().plusDays(1));
        return tomorrow+"_SE"+area;
    }

    public int getHour(){
        LocalTime time = LocalTime.now();
        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("HH");
        String  formatedTime = time.format(dtf2);

        return Integer.parseInt(formatedTime);
    }

    public String formatDate(LocalDate date){
        return date.format(DateTimeFormatter.ofPattern("yyyy/MM-dd"));
    }

    public String formatDateAndTime(OffsetDateTime time){
        return time.format(DateTimeFormatter.ofPattern("dd/MM 'kl.' HH:mm"));
    }

}
