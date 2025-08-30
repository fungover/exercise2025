package cheap.electricity.services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class UrlFormatter {
  private String zone = "SE3";
  private static final String BASE = "https://www.elprisetjustnu.se/api/v1/prices/";
  private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("yyyy/MM-dd");

  public UrlFormatter(String zone){
    setZone(zone == null ? "SE3" : zone);
  }

  public String formatUrl(){
    LocalDate today = LocalDate.now(java.time.ZoneId.of("Europe/Stockholm"));
    return formatUrl(today);
  }

  public String formatUrl(LocalDate date){
    String datePart = date.format(DATE_FMT);
    return BASE + datePart + "_" + zone + ".json";
    }

  public void setZone(String zone) {
    this.zone = zone;
  }

  public String getZone() {
    return zone;
  }
}