package cheap.electricity.services;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class UrlFormatter {
  private String zone = "SE3";
  private LocalDate selectedDate;
  private static final String BASE = "https://www.elprisetjustnu.se/api/v1/prices/";
  private static final java.util.Set<String> VALID_ZONES = java.util.Set.of("SE1","SE2","SE3","SE4");
  private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("yyyy/MM-dd");

  public UrlFormatter(String zone){
    setZone(zone == null ? "SE3" : zone);
    this.selectedDate = LocalDate.now(ZoneId.of("Europe/Stockholm"));
  }

  public String formatUrl(){
    return formatUrl(selectedDate);
  }

  public String formatTomorrowUrl() {
    return formatUrl(LocalDate.now(ZoneId.of("Europe/Stockholm")).plusDays(1));
  }

  public String formatUrl(LocalDate date){
    this.selectedDate = date;
    String datePart = date.format(DATE_FMT);
    return BASE + datePart + "_" + zone + ".json";
    }

  public void setZone(String zone) {
    if (zone == null) throw new IllegalArgumentException("zone must not be null");
    String z = zone.trim().toUpperCase();
    if (!VALID_ZONES.contains(z)) {
      throw new IllegalArgumentException("Invalid zone: " + zone + " (allowed: SE1–SE4)");
      }
    this.zone = z;
    }

  public String getZone() {
    return zone;
  }

  public LocalDate getSelectedDate() {
    return selectedDate;
  }

  public String getSelectedDateLabel() {
    return selectedDate != null
            ? selectedDate + " (" + selectedDate.getDayOfWeek() + ")"
            : "No date selected";
  }
}