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
    System.out.println("----------");
    System.out.println("Current zone is (" + day.getZone() + ")");
    System.out.println("----------");
    System.out.println("Choose from the menu:");
    System.out.println("1. Download prices(day1, day2)");
    System.out.println("2. Show mean price(24h)");
    System.out.println("3. Show price highest and lowest(h)");
    System.out.println("4. The best time to charge a car");
    System.out.println("5. Choose zon");
    System.out.println("6. Exit");

    String input = System.console().readLine();
    switch(input) {
      case "1":
        priceAnalyzer.getPrices();
        showMenu(priceAnalyzer, day);
        break;
      case "2":
        priceAnalyzer.showMeanPrice();
        showMenu(priceAnalyzer, day);
        break;
      case"3":
        priceAnalyzer.HighLowPrice();
        showMenu(priceAnalyzer, day);
        break;
      case"4":
        priceAnalyzer.showBestChargingTime();
        showMenu(priceAnalyzer, day);
        break;
      case "5":
        System.out.println("Current zone is: " + day.getZone());
        System.out.println("""
                        Zones available:
                        1. SE1 = Luleå / Norra Sverige
                        2. SE2 = Sundsvall / Norra Mellansverige
                        3. SE3 = Stockholm / Södra Mellansverige
                        4. SE4 = Malmö / Södra Sverige\\s""\")
                        5. Cancel
                        """);
        String input2 = System.console().readLine();
        switch(input2) {
          case "1":
            day.setZone("SE1");
            System.out.println("Zone set to SE1 (Don't forget to download the new prices)");
            priceAnalyzer.setUrl(day.formatUrl());
            showMenu(priceAnalyzer, day);
            break;
          case "2":
            day.setZone("SE2");
            System.out.println("Zone set to SE2 (Don't forget to download the new prices)");
            priceAnalyzer.setUrl(day.formatUrl());
            showMenu(priceAnalyzer, day);
            break;
          case "3":
            day.setZone("SE3");
            System.out.println("Zone set to SE3 (Don't forget to download the new prices)");
            priceAnalyzer.setUrl(day.formatUrl());
            showMenu(priceAnalyzer, day);
            break;
          case "4":
            day.setZone("SE4");
            System.out.println("Zone set to SE4 (Don't forget to download the new prices)");
            priceAnalyzer.setUrl(day.formatUrl());
            showMenu(priceAnalyzer, day);
            break;
          case "5":
            showMenu(priceAnalyzer, day);
            break;
        }
      case "6":
        return;
      default:
        System.out.println("Invalid option");
        showMenu(priceAnalyzer, day);
    }
  }
}
