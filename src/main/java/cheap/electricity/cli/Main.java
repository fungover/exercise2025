package cheap.electricity.cli;

import cheap.electricity.services.PriceAnalyzer;
import cheap.electricity.services.UrlFormatter;

import java.io.IOException;

public class Main {
  public static void main(String[] args) throws IOException, InterruptedException {
    System.out.println("Let's check when it's better to start charging");
    System.out.println("Press y to start");

    var console = System.console();
      String input = console != null ? console.readLine() : new java.util.Scanner(System.in).nextLine();
      if ("y".equalsIgnoreCase(input != null ? input.trim() : "")) {
      UrlFormatter day = new UrlFormatter("SE3");
      String url = day.formatUrl();

      PriceAnalyzer priceAnalyzer = new PriceAnalyzer(url);
      showMenu(priceAnalyzer, day);
    }
  }

  static void showMenu(PriceAnalyzer priceAnalyzer, UrlFormatter day) throws IOException, InterruptedException {
    var console = System.console();
    final java.util.Scanner scanner = console == null ? new java.util.Scanner(System.in) : null;
    while (true) {
      System.out.println("----------");
      System.out.println("Current zone is (" + day.getZone() + ")");
      System.out.println("----------");
      System.out.println("Choose from the menu:");
      System.out.println("1. Download prices (today; next day if available)");
      System.out.println("2. Show mean price (24h)");
      System.out.println("3. Show hours with highest and lowest prices");
      System.out.println("4. Best time to charge (2/4/8h)");
      System.out.println("5. Change zone");
      System.out.println("6. Exit");
      String input = console != null ? console.readLine() : scanner.nextLine();
      input = input != null ? input.trim() : "";
      switch (input) {
        case "1" -> priceAnalyzer.getPrices();
        case "2" -> priceAnalyzer.showMeanPrice();
        case "3" -> priceAnalyzer.HighLowPrice();
        case "4" -> priceAnalyzer.showBestChargingTime();
        case "5" -> {
          System.out.println("Current zone is: " + day.getZone());
          System.out.println("""
                    Zones available:
                    1. SE1 = Luleå / Norra Sverige
                    2. SE2 = Sundsvall / Norra Mellansverige
                    3. SE3 = Stockholm / Södra Mellansverige
                    4. SE4 = Malmö / Södra Sverige
                    5. Cancel
                    """);
          String input2 = console != null ? console.readLine() : scanner.nextLine();
          input2 = input2 != null ? input2.trim() : "";
          switch (input2) {
            case "1" -> {
              day.setZone("SE1");
              System.out.println("Zone set to SE1 (Don't forget to download the new prices)");
              priceAnalyzer.setUrl(day.formatUrl());
            }
            case "2" -> {
              day.setZone("SE2");
              System.out.println("Zone set to SE2 (Don't forget to download the new prices)");
              priceAnalyzer.setUrl(day.formatUrl());
            }
            case "3" -> {
              day.setZone("SE3");
              System.out.println("Zone set to SE3 (Don't forget to download the new prices)");
              priceAnalyzer.setUrl(day.formatUrl());
            }
            case "4" -> {
              day.setZone("SE4");
              System.out.println("Zone set to SE4 (Don't forget to download the new prices)");
              priceAnalyzer.setUrl(day.formatUrl());
            }
            case "5" -> {
            }
            default -> System.out.println("Invalid option");
          }
        }
        case "6" -> { return; }
        default -> System.out.println("Invalid option");
      }
    }
  }
}