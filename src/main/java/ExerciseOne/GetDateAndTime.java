package ExerciseOne;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class GetDateAndTime {

    private final ZoneId zoneId = ZoneId.of("Europe/Stockholm");

    public String getToday(int area) {

        String today = formatDate(LocalDate.now(this.zoneId));
        return today + "_SE" + area;
    }

    public String getTomorrow(int area) {
        String tomorrow = formatDate(LocalDate.now(this.zoneId).plusDays(1));
        return tomorrow + "_SE" + area;
    }

    public int getHour(){
        LocalTime time = LocalTime.now(this.zoneId);
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
