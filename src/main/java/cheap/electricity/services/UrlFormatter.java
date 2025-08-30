package cheap.electricity.services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class UrlFormatter {
  private String zone = "SE3";

  public UrlFormatter(String zone){
    setZone(zone == null ? "SE3" : zone);
  }

  public String formatUrl(){
    LocalDate today = LocalDate.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM-dd");
    String datePart = today.format(formatter);
    return "https://www.elprisetjustnu.se/api/v1/prices/" + datePart + "_" + zone + ".json";
  }

  public void setZone(String zone) {
    this.zone = zone;
  }

  public String getZone() {
    return zone;
  }
}
