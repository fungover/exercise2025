package cheap.electricity.cli;

import cheap.electricity.services.PriceAnalyzer;
import cheap.electricity.services.UrlFormatter;

import java.io.IOException;
import java.time.LocalDate;

public class Main {
  public static void main(String[] args) throws IOException, InterruptedException {
    System.out.println("Let's check when it's better to start charging");
    System.out.println("Press y to start");

    var console = System.console();
    final java.util.Scanner scanner = console == null ? new java.util.Scanner(System.in) : null;
    String input = console != null ? console.readLine() : scanner.nextLine();
      if ("y".equalsIgnoreCase(input != null ? input.trim() : "")) {
        UrlFormatter formatter = new UrlFormatter("SE3");
        PriceAnalyzer priceAnalyzer = new PriceAnalyzer(formatter.formatUrl());
        try {
          priceAnalyzer.getPrices();
          } catch (Exception e) {
          System.out.println("Failed to download today's prices: " + e.getMessage());
          }
        showMenu(priceAnalyzer, formatter, scanner);
      } else {
        System.out.println("Exiting.");
        return;
        }
  }

  static void showMenu(PriceAnalyzer priceAnalyzer,
                       UrlFormatter formatter,
                       java.util.Scanner scanner) throws IOException, InterruptedException {
    var console = System.console();
    while (true) {
      System.out.println("----------");
      System.out.println("Current zone is (" + formatter.getZone() + ") " + formatter.getSelectedDateLabel());
      System.out.println("----------");
      System.out.println("Choose from the menu:");
      System.out.println("1. Download prices");
      System.out.println("2. Show mean price (24h)");
      System.out.println("3. Show hours with highest and lowest prices");
      System.out.println("4. Best time to charge (shows 2/4/8h windows)");
      System.out.println("5. Change zone");
      System.out.println("6. Exit");
      String input = console != null ? console.readLine() : scanner.nextLine();
      input = input != null ? input.trim() : "";
      switch (input) {
        case "1" -> {
          System.out.println("""
        Choose day to download:
        1. Today
        2. Tomorrow
        3. Enter custom date (yyyy-MM-dd)
        4. Cancel
        """);
          String dayChoice = console != null ? console.readLine() : scanner.nextLine();
          dayChoice = dayChoice != null ? dayChoice.trim() : "";
          switch (dayChoice) {
            case "1" -> {
              try {
                priceAnalyzer.setUrl(formatter.formatTodayUrl());
                priceAnalyzer.getPrices();
                System.out.println("Today's prices loaded.");
                } catch (Exception e) {
                System.out.println("Failed to download today's prices: " + e.getMessage());
                }
            }
            case "2" -> {
              try {
                priceAnalyzer.setUrl(formatter.formatTomorrowUrl());
                priceAnalyzer.getPrices();
                System.out.println("Tomorrow's prices loaded.");
                } catch (Exception e) {
                System.out.println("Failed to download tomorrow's prices: " + e.getMessage());
                }
            }
            case "3" -> {
              System.out.println("Enter date (yyyy-MM-dd):");
              String dateStr = console != null ? console.readLine() : scanner.nextLine();
              dateStr = dateStr != null ? dateStr.trim() : "";
              try {
                LocalDate date = LocalDate.parse(dateStr);
                priceAnalyzer.setUrl(formatter.formatUrl(date));
                priceAnalyzer.getPrices();
                System.out.println("Prices for " + date + " loaded.");
              } catch (Exception e) {
                System.out.println("Invalid date format or data unavailable: " + e.getMessage());
              }
            }
            case "4" -> System.out.println("Canceled.");
            default -> System.out.println("Invalid option.");
          }
        }
        case "2" -> priceAnalyzer.showMeanPrice();
        case "3" -> priceAnalyzer.HighLowPrice();
        case "4" -> priceAnalyzer.showBestChargingTime();
        case "5" -> {
          System.out.println("Current zone is: " + formatter.getZone());
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
              formatter.setZone("SE1");
              System.out.println("Zone set to SE1 (Don't forget to download the new prices)");
              priceAnalyzer.setUrl(formatter.formatUrl());
            }
            case "2" -> {
              formatter.setZone("SE2");
              System.out.println("Zone set to SE2 (Don't forget to download the new prices)");
              priceAnalyzer.setUrl(formatter.formatUrl());
            }
            case "3" -> {
              formatter.setZone("SE3");
              System.out.println("Zone set to SE3 (Don't forget to download the new prices)");
              priceAnalyzer.setUrl(formatter.formatUrl());
            }
            case "4" -> {
              formatter.setZone("SE4");
              System.out.println("Zone set to SE4 (Don't forget to download the new prices)");
              priceAnalyzer.setUrl(formatter.formatUrl());
            }
            case "5" -> System.out.println("Zone change canceled");
            default -> System.out.println("Invalid option");
          }
        }
        case "6" -> { return; }
        default -> System.out.println("Invalid option");
      }
    }
  }
}