package ExerciseOne;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class GetDate {

    public void getDate(int area) throws IOException, InterruptedException {

        Request req = new Request();

        String today = formatDate(LocalDate.now());
        String search = today+"_SE"+area;

       req.request(search);

        LocalTime time = LocalTime.now();
        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("HH");
        String  formatedTime = time.format(dtf2);

        int timeToday = Integer.parseInt(formatedTime);

        if(timeToday >= 13){
            String tomorrow = formatDate(LocalDate.now().plusDays(1));
            String searchTomorrow = tomorrow+"_SE"+area;
            req.request(searchTomorrow);
        }
    }

    public String formatDate(LocalDate date){
        return date.format(DateTimeFormatter.ofPattern("yyyy/MM-dd"));
    }

}
